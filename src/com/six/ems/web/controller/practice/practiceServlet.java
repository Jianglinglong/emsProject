package com.six.ems.web.controller.practice;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.Blank;
import com.six.ems.entity.tables.Choice;
import com.six.ems.entity.tables.Course;
import com.six.ems.entity.tables.Judge;
import com.six.ems.entity.tables.Paper;
import com.six.ems.entity.tables.PaperRule;
import com.six.ems.entity.tables.Subjective;
import com.six.ems.entity.utils.QuestionPaper;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.StringUtils;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.course.CourseServiceImpl;
import com.six.ems.web.service.impl.paper.PaperRuleServiceImpl;
import com.six.ems.web.service.impl.questions.BlankServiceImpl;
import com.six.ems.web.service.impl.questions.ChoiceServiceImpl;
import com.six.ems.web.service.impl.questions.JudgeServiceImpl;
import com.six.ems.web.service.impl.questions.SubjectiveServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;

public class practiceServlet extends BaseServlet {
    public String initPractice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "WEB-INF/jsp/practice/practice.jsp";
    }

    public String practice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type = request.getParameter("type");
        String url = "/WEB-INF/jsp/paper/showPaper.jsp";
        PaperRule paperRule = new PaperRule();
        Paper paper = new Paper();
        if (StringUtils.isNotEmpty(type)) {
            switch (type) {
                case "0":
                    break;
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    break;
                case "4":
                    break;
                case "5":
                    break;
                default:
                    url = "";
            }
        }
        return url;
    }
    
    public void getSuject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String subjectType = request.getParameter("ctype");
    	String courseId = request.getParameter("courseId");
    	String subjectNum = request.getParameter("practiceNum");
    	Integer number = Integer.valueOf(subjectNum);
    	List<?> subList = null;
    	switch (subjectType) {
		case "choice":
			List<Choice> allChoice = new ChoiceServiceImpl().getAllChoice(0, Integer.valueOf(courseId));
	    	Collections.shuffle(allChoice);
	    	subList = allChoice.subList(0, number > allChoice.size() ? allChoice.size() : number);
			request.getSession().setAttribute("choices", subList);
			break;
		case "multiple":
			List<Choice> allMChoice = new ChoiceServiceImpl().getAllChoice(1, Integer.valueOf(courseId));
	    	Collections.shuffle(allMChoice);
	    	subList = allMChoice.subList(0, number > allMChoice.size() ? allMChoice.size() : number);
	    	request.getSession().setAttribute("mulChoices", subList);
			break;
		case "blank":
			List<Blank> allBlank = new BlankServiceImpl().getAllBlank();
			Collections.shuffle(allBlank);
	    	subList = allBlank.subList(0, number > allBlank.size() ? allBlank.size() : number);
	    	request.getSession().setAttribute("blanks", subList);
			break;
		case "judge":
			List<Judge> allJudge = new JudgeServiceImpl().getAllJudge();
			Collections.shuffle(allJudge);
	    	subList = allJudge.subList(0, number > allJudge.size() ? allJudge.size() : number);
	    	request.getSession().setAttribute("judges", subList);
			break;
		case "subjective":
			List<Subjective> allSubjective = new SubjectiveServiceImpl().getAllSubjective();
			Collections.shuffle(allSubjective);
	    	subList = allSubjective.subList(0, number > allSubjective.size() ? allSubjective.size() : number);
	    	request.getSession().setAttribute("subjectives", subList);
			break;
		default:
			break;
		}
    	String url = "practice.do?method=initSuject";
    	if(CollectionUtils.isNotBlank(subList)) {
    		response.getWriter().print(url);
    	} else {
    		response.getWriter().print("NO");
    	}
    }
    @SuppressWarnings("unchecked")
    public String initSuject(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		List<Choice> choice = (List<Choice>) request.getSession().getAttribute("choices");
    	List<Choice> mulChoice = (List<Choice>) request.getSession().getAttribute("mulChoices");
    	List<Blank> blank = (List<Blank>) request.getSession().getAttribute("blanks");
    	List<Judge> judge = (List<Judge>) request.getSession().getAttribute("judges");
    	List<Subjective> subjective = (List<Subjective>) request.getSession().getAttribute("subjectives");
    	request.getSession().removeAttribute("choices");
    	request.getSession().removeAttribute("mulChoices");
    	request.getSession().removeAttribute("blanks");
    	request.getSession().removeAttribute("judges");
    	request.getSession().removeAttribute("subjectives");
    	if(CollectionUtils.isNotBlank(choice)) {
    		request.setAttribute("choices", choice);
    		request.setAttribute("choicesNum", choice.size());
    		request.setAttribute("practiceTitle", "单选题练习");
    	}
    	if(CollectionUtils.isNotBlank(mulChoice)) {
    		request.setAttribute("mulChoices", mulChoice);
    		request.setAttribute("mulChoicesNum", mulChoice.size());
    		request.setAttribute("practiceTitle", "多选题练习");
    	}
    	if(CollectionUtils.isNotBlank(blank)) {
    		request.setAttribute("blanks", blank);
    		request.setAttribute("blanksNum", blank.size());
    		request.setAttribute("practiceTitle", "填空题练习");
    	}
    	if(CollectionUtils.isNotBlank(judge)) {
    		request.setAttribute("judges", judge);
    		request.setAttribute("judgesNum", judge.size());
    		request.setAttribute("practiceTitle", "判断题练习");
    	}
    	if(CollectionUtils.isNotBlank(subjective)) {
    		request.setAttribute("subjectives", subjective);
    		request.setAttribute("subjectivesNum", subjective.size());
    		request.setAttribute("practiceTitle", "主观题练习");
    	}
    	
    	
    	
    	return "/WEB-INF/jsp/practice/practiceExam.jsp";
    }
    
    public String initexamination(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer courseId = Integer.valueOf(request.getParameter("courseId"));
    	
    	String choiceCk = new PaperRuleServiceImpl().getQuestionsToPaper("15", "2",courseId, "choice");
        String multipleCk = new PaperRuleServiceImpl().getQuestionsToPaper("5", "4",courseId, "multiple");
        String blankCk = new PaperRuleServiceImpl().getQuestionsToPaper("5", "2",courseId, "blank");
        String judgeCk = new PaperRuleServiceImpl().getQuestionsToPaper("5", "5",courseId, "judge");
        String subjectiveCk = new PaperRuleServiceImpl().getQuestionsToPaper("3", "5",courseId, "subjective");
    	
        AdminService adminService = new AdminServiceImpl();
		Map<String, SelectItem> condition = CollectionUtils.getCondition();
		Course course = new CourseServiceImpl().getCourseByID(courseId);
		request.setAttribute("course", course);
        
        // 单选题
        List<Choice> choices = new ArrayList<>();
        List<QuestionPaper> choiceQues = JSONArray.parseArray(choiceCk, QuestionPaper.class);
        condition.clear();
		for (QuestionPaper choiceQue : choiceQues) {
			condition.put("choice_id", new SelectItem(choiceQue.getId()));
			List<Choice> query = AdvanceUtil.select(Choice.class, condition);
			if (CollectionUtils.isNotBlank(query)) {
				choices.add(query.get(0));
			}
		}
        
        request.setAttribute("choices", choices); // 单选择题内容
		request.setAttribute("singleChoiceNum", 15); // 单选题总个数
		int singS = 30;
		request.setAttribute("singleScore", singS); // 单选题总分数
		
		// 多选题
		List<Choice> mulChoices = new ArrayList<>();
		List<QuestionPaper> mulChoiceQues = JSONArray.parseArray(multipleCk, QuestionPaper.class);
		condition.clear();
		for (QuestionPaper questionPaper : mulChoiceQues) {
			condition.put("choice_id", new SelectItem(questionPaper.getId()));
			List<Choice> query = AdvanceUtil.select(Choice.class, condition);
			if (CollectionUtils.isNotBlank(query)) {
				mulChoices.add(query.get(0));
			}
		}
		request.setAttribute("mulChoices", mulChoices);
		request.setAttribute("mulChoiceNum", 5);
		int mulS = 20;
		request.setAttribute("mulScore", mulS);
		
		// 判断题
		List<Judge> judges = new ArrayList<>();
		List<QuestionPaper> judgeQues = JSONArray.parseArray(judgeCk, QuestionPaper.class);
		condition.clear();
		for (QuestionPaper judgeQue : judgeQues) {
			condition.put("judge_id", new SelectItem(judgeQue.getId()));
			List<Judge> query = AdvanceUtil.select(Judge.class, condition);
			if (CollectionUtils.isNotBlank(query)) {
				judges.add(query.get(0));
			}
		}
		request.setAttribute("judges", judges);
		request.setAttribute("judgeChoiceNum", 5);
		int judgeS = 10;
		request.setAttribute("judgeScore", judgeS);
		
		// 填空题
		List<Blank> blanks = new ArrayList<>();
		List<QuestionPaper> blankQues = JSONArray.parseArray(blankCk, QuestionPaper.class);
		condition.clear();
		for (QuestionPaper blankQue : blankQues) {
			condition.put("blank_id", new SelectItem(blankQue.getId()));
			List<Blank> query = AdvanceUtil.select(Blank.class, condition);
			if (CollectionUtils.isNotBlank(query)) {
				blanks.add(query.get(0));
			}
		}
		request.setAttribute("blanks", blanks);
		request.setAttribute("blankChoiceNum", 5);
		int blankS = 25;
		request.setAttribute("blankScore", blankS);

		// 主观题
		List<Subjective> subjectives = new ArrayList<>();
		List<QuestionPaper> subQues = JSONArray.parseArray(subjectiveCk, QuestionPaper.class);
		condition.clear();
		for (QuestionPaper subQue : subQues) {
			condition.put("sub_id", new SelectItem(subQue.getId()));
			List<Subjective> query = AdvanceUtil.select(Subjective.class, condition);
			if (CollectionUtils.isNotBlank(query)) {
				subjectives.add(query.get(0));
			}
		}
		request.setAttribute("subjectives", subjectives);
		request.setAttribute("subChoiceNum", 3);
		int subS = 15;
		request.setAttribute("subScore", subS);
		
		return "/WEB-INF/jsp/practice/praExam.jsp";
    }

   
}
