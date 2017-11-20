package com.six.ems.web.controller.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.*;
import com.six.ems.entity.utils.QuestionAnswer;
import com.six.ems.entity.utils.QuestionPaper;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.StringUtils;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.ExamServiceImpl;
import com.six.ems.web.service.impl.course.CourseServiceImpl;
import com.six.ems.web.service.impl.paper.PaperRuleServiceImpl;
import com.six.ems.web.service.impl.paper.PaperServiceImpl;
import com.six.ems.web.service.impl.questions.BlankServiceImpl;
import com.six.ems.web.service.impl.questions.ChoiceServiceImpl;
import com.six.ems.web.service.impl.questions.JudgeServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;
import com.six.ems.web.service.interfaces.questions.BlankService;
import com.six.ems.web.service.interfaces.questions.ChoiceService;
import com.six.ems.web.service.interfaces.questions.JudgeService;
import com.six.ems.web.service.interfaces.users.AdminService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

public class StudentExamServlet extends BaseServlet {
	public void stuCommitExam(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ChoiceService choiceService = new ChoiceServiceImpl();
		JudgeService judgeService = new JudgeServiceImpl();
		BlankService blankService = new BlankServiceImpl();

		String paperID = request.getParameter("paperID");
		Integer examId = Integer.valueOf(paperID);

		String courseID = request.getParameter("courseID");
		Object userId = request.getSession().getAttribute("userId");
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		Integer stuId = new UserServiceImpl().getUserByUserId((Integer) userId).getStuId();
		AdminServiceImpl adminService = new AdminServiceImpl();
		condition.put("exam_id", new SelectItem(examId));
		Exam exam = adminService.getTypeByCondition(Exam.class, condition).get(0);
		condition.clear();
		condition.put("stu_id", new SelectItem(stuId));
		UserClass userClass = adminService.getTypeByCondition(UserClass.class, condition).get(0);
		ErrorTable errorTable = new ErrorTable();
		errorTable.setStuId(stuId);
		List<QuestionAnswer> answers = new ArrayList<>();
		double score = 0;
		Answer stuAnswer = new Answer();
		Score stuScore = new Score();
		if (StringUtils.isNotEmpty(paperID)) {
			Integer paperID1 = exam.getPaperID();
			errorTable.setExamId(examId);
			stuScore.setPaperId(paperID1);
			stuScore.setStuId(stuId);
			stuScore.setCourseId(Integer.valueOf(courseID));

			stuAnswer.setPaperId(paperID1);
			stuAnswer.setStuId(stuId);
			// 主观题

			String[] subjectives = request.getParameterValues("subjective");
			if (subjectives != null) {
				for (String subID : subjectives) {
					String subjectiveAnswer = request.getParameter("subjectiveNum" + subID);
					QuestionAnswer stuSubjective = new QuestionAnswer();
					stuSubjective.setId(Integer.valueOf(subID));
					stuSubjective.setChecked(subjectiveAnswer);
					answers.add(stuSubjective);
				}

				String subjectiveJson = JSON.toJSONString(answers);
				answers.clear();
				stuAnswer.setSubId(subjectiveJson);
			}
		}
		// 填空题
		String[] blanks = request.getParameterValues("blank");
		if (blanks != null) {
			for (String blankID : blanks) {
				Integer id = Integer.valueOf(blankID);
				String blankScore = request.getParameter("blankScore" + blankID);
				String blankAnswer = request.getParameter("blankNum" + blankID);
				Blank blank = blankService.getBlankByBlankId(id);
				QuestionAnswer stuBlank = new QuestionAnswer();
				stuBlank.setId(id);
				stuBlank.setChecked(blankAnswer);
				if (blank.getAnswer().equals(blankAnswer)) {
					score += Double.parseDouble(blankScore);
				} else {
					errorTable.setBlankId(id);
					adminService.add(errorTable);
				}
				answers.add(stuBlank);
			}
		}
		stuAnswer.setBlankId(JSON.toJSONString(answers));
		// 判断题
		errorTable.setBlankId(null);
		answers.clear();
		String[] judges = request.getParameterValues("judge");
		if (judges != null) {
			for (String judgeID : judges) {
				Integer id = Integer.valueOf(judgeID);
				String judgeScore = request.getParameter("judgeScore" + judgeID);
				String judgeAnswer = request.getParameter("judgeNum" + judgeID);
				Judge judge = judgeService.getJudgeByJudgeId(id);
				if (judge.getAnswer().equals(judgeAnswer)) {
					score += Double.parseDouble(judgeScore);
				} else {
					errorTable.setJudgeId(id);
					adminService.add(errorTable);
				}
				QuestionAnswer stuJudge = new QuestionAnswer();
				stuJudge.setChecked(judgeAnswer);
				stuJudge.setId(id);
				answers.add(stuJudge);
			}
		}
		stuAnswer.setJudgeId(JSON.toJSONString(answers));
		// 多选题
		errorTable.setJudgeId(null);
		answers.clear();
		String[] multis = request.getParameterValues("multi");
		if (multis != null) {
			for (String multiID : multis) {
				String multiScore = request.getParameter("multiScore" + multiID);
				String[] multAnswers = request.getParameterValues("multChoice" + multiID);
				String multAnswer = "";
				if (multAnswers != null) {
					for (String ma : multAnswers) {
						multAnswer += ma;
					}
				}
				Integer id = Integer.valueOf(multiID);
				Choice choice = choiceService.getChoiceByChoiceId(id);
				if (choice.getAnswer().equals(multAnswer)) {
					score += Double.parseDouble(multiScore);
				} else {
					errorTable.setMChoiceId(id);
					adminService.add(errorTable);
				}
				QuestionAnswer stuMultChoice = new QuestionAnswer();
				stuMultChoice.setId(id);
				stuMultChoice.setChecked(multAnswer);
				answers.add(stuMultChoice);
			}
		}
		// 单选题
		String[] singles = request.getParameterValues("single");
		if (singles != null) {
			for (String singleID : singles) {
				Integer id = Integer.valueOf(singleID);
				String singleAnswer = request.getParameter("singleChoice" + singleID);
				String singleScore = request.getParameter("singleScore" + singleID);
				Choice choice = choiceService.getChoiceByChoiceId(id);
				if (choice.getAnswer().equals(singleAnswer)) {
					score += Double.parseDouble(singleScore);
				} else {
					errorTable.setSChoiceId(id);
					adminService.add(errorTable);
				}
				QuestionAnswer stuSingle = new QuestionAnswer();
				stuSingle.setId(id);
				stuSingle.setChecked(singleAnswer);
				answers.add(stuSingle);
			}
		}
		stuAnswer.setChoiceId(JSON.toJSONString(answers));
		boolean add = adminService.add(stuAnswer);
		stuScore.setAutoScore(score);
		stuScore.setTotalScore(score);
		add = adminService.add(stuScore);
		response.getWriter().print(add ? "提交成功,得分：" + score : "提交失败");
	}

	public String initStartExam(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String paperId = request.getParameter("paperID");
		request.setAttribute("paperID", paperId);
		Object userId = request.getSession().getAttribute("userId");
		AdminService adminService = new AdminServiceImpl();
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		condition.put("user_id", new SelectItem(userId));
		User user = adminService.getTypeByCondition(User.class, condition).get(0);
		condition.clear();
		condition.put("paper_id", new SelectItem(paperId));
		condition.put("stu_id", new SelectItem(user.getStuId()));
		List<Answer> answers = adminService.getTypeByCondition(Answer.class, condition);
		if (CollectionUtils.isNotBlank(answers)) {
			request.setAttribute("paper", false);
		} else {
			request.setAttribute("paper", true);
			Paper paperById = new PaperServiceImpl().getPaperById(Integer.valueOf(paperId));
			Course course = new CourseServiceImpl().getCourseByID(paperById.getCourseID());
			request.setAttribute("course", course);
			PaperRule paperRule = new PaperRuleServiceImpl().getPaperRuleByRuleId(paperById.getRuleID());
			Integer paperTime = paperById.getPaperTime();
			// int minutes = paperTime % 60;
			// int hours = paperTime / 60;
			// String times = new String(hours + ":"+minutes);
			request.setAttribute("times", paperTime);
			///////////////////////////////////////////////////////////////////////////////////
			// 单选题
			List<Choice> choices = new ArrayList<>();
			String singleChoiceChecked = paperRule.getSingleChoiceChecked();
			Integer singleChoiceNum = paperRule.getSingleChoiceNum();
			BigDecimal singleScore = paperRule.getSingleScore();
			List<QuestionPaper> choiceQues = JSONArray.parseArray(singleChoiceChecked, QuestionPaper.class);
			if (!singleChoiceChecked.equals("") && singleChoiceNum != 0 && singleScore.doubleValue() != 0) {
				condition.clear();
				for (QuestionPaper choiceQue : choiceQues) {
					condition.put("choice_id", new SelectItem(choiceQue.getId()));
					List<Choice> query = AdvanceUtil.select(Choice.class, condition);
					if (CollectionUtils.isNotBlank(query)) {
						choices.add(query.get(0));
					}
				}
				request.setAttribute("choices", choices); // 单选择题内容
				request.setAttribute("choiceQues", choiceQues); // 单选题单个分数
				request.setAttribute("singleChoiceNum", singleChoiceNum); // 单选题总个数
				if (paperRule.getRuleType() == 0) {
					int singS = (int) (singleChoiceNum * choiceQues.get(0).getScore());
					request.setAttribute("singleScore", singS); // 单选题总分数
				} else {
					request.setAttribute("singleScore", singleScore);
				}
			}

			// 多选题
			List<Choice> mulChoices = new ArrayList<>();
			String mulChoiceChecked = paperRule.getMulChoiceChecked();
			Integer mulChoiceNum = paperRule.getMulChoiceNum();
			BigDecimal mulScore = paperRule.getMulScore();
			List<QuestionPaper> mulChoiceQues = JSONArray.parseArray(mulChoiceChecked, QuestionPaper.class);
			if (!mulChoiceChecked.equals("") && mulChoiceNum != 0 && mulScore.doubleValue() != 0) {
				condition.clear();
				for (QuestionPaper questionPaper : mulChoiceQues) {
					condition.put("choice_id", new SelectItem(questionPaper.getId()));
					List<Choice> query = AdvanceUtil.select(Choice.class, condition);
					if (CollectionUtils.isNotBlank(query)) {
						mulChoices.add(query.get(0));
					}
				}
				request.setAttribute("mulChoices", mulChoices);
				request.setAttribute("mulChoiceQues", mulChoiceQues);
				request.setAttribute("mulChoiceNum", mulChoiceNum);

				if (paperRule.getRuleType() == 0) {
					int mulS = (int) (mulChoiceNum * mulChoiceQues.get(0).getScore());
					request.setAttribute("mulScore", mulS);
				} else {
					request.setAttribute("mulScore", mulScore);
				}
			}

			// 判断题
			List<Judge> judges = new ArrayList<>();
			String judgeChoiceChecked = paperRule.getJudgeChecked();
			Integer judgeChoiceNum = paperRule.getJudgeNum();
			BigDecimal judgeScore = paperRule.getJudgeScore();
			List<QuestionPaper> judgeQues = JSONArray.parseArray(judgeChoiceChecked, QuestionPaper.class);
			if (!judgeChoiceChecked.equals("") && judgeChoiceNum != 0 && judgeScore.doubleValue() != 0) {
				condition.clear();
				for (QuestionPaper judgeQue : judgeQues) {
					condition.put("judge_id", new SelectItem(judgeQue.getId()));
					List<Judge> query = AdvanceUtil.select(Judge.class, condition);
					if (CollectionUtils.isNotBlank(query)) {
						judges.add(query.get(0));
					}
				}
				request.setAttribute("judges", judges);
				request.setAttribute("judgeQues", judgeQues);
				request.setAttribute("judgeChoiceNum", judgeChoiceNum);

				if (paperRule.getRuleType() == 0) {
					int judgeS = (int) (judgeChoiceNum * judgeQues.get(0).getScore());
					request.setAttribute("judgeScore", judgeS);
				} else {
					request.setAttribute("judgeScore", judgeScore);
				}
			}

			// 填空题
			List<Blank> blanks = new ArrayList<>();
			String blankChoiceChecked = paperRule.getFileBlankChoiceChecked();
			Integer blankChoiceNum = paperRule.getFileBlankChoiceNum();
			BigDecimal blankScore = paperRule.getFileBlankScore();
			List<QuestionPaper> blankQues = JSONArray.parseArray(blankChoiceChecked, QuestionPaper.class);
			if (!blankChoiceChecked.equals("") && blankChoiceNum != 0 && blankScore.doubleValue() != 0) {
				condition.clear();
				for (QuestionPaper blankQue : blankQues) {
					condition.put("blank_id", new SelectItem(blankQue.getId()));
					List<Blank> query = AdvanceUtil.select(Blank.class, condition);
					if (CollectionUtils.isNotBlank(query)) {
						blanks.add(query.get(0));
					}
				}
				request.setAttribute("blanks", blanks);
				request.setAttribute("blankQues", blankQues);
				request.setAttribute("blankChoiceNum", blankChoiceNum);

				if (paperRule.getRuleType() == 0) {
					int blankS = (int) (blankChoiceNum * blankQues.get(0).getScore());
					request.setAttribute("blankScore", blankS);
				} else {
					request.setAttribute("blankScore", blankScore);
				}
			}

			// 主观题
			List<Subjective> subjectives = new ArrayList<>();
			String subChoiceChecked = paperRule.getSubChoiceChecked();
			Integer subChoiceNum = paperRule.getSubChoiceNum();
			BigDecimal subScore = paperRule.getSubScore();
			List<QuestionPaper> subQues = JSONArray.parseArray(subChoiceChecked, QuestionPaper.class);
			if (!subChoiceChecked.equals("") && subChoiceNum != 0 && subScore.doubleValue() != 0) {
				condition.clear();
				for (QuestionPaper subQue : subQues) {
					condition.put("sub_id", new SelectItem(subQue.getId()));
					List<Subjective> query = AdvanceUtil.select(Subjective.class, condition);
					if (CollectionUtils.isNotBlank(query)) {
						subjectives.add(query.get(0));
					}
				}
				request.setAttribute("subjectives", subjectives);
				request.setAttribute("subQues", subQues);
				request.setAttribute("subChoiceNum", subChoiceNum);

				if (paperRule.getRuleType() == 0) {
					int subS = (int) (subChoiceNum * subQues.get(0).getScore());
					request.setAttribute("subScore", subS);
				} else {
					request.setAttribute("subScore", subScore);
				}
			}

		}
		return "/WEB-INF/jsp/student/startExam.jsp";
	}
}