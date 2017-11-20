package com.six.ems.web.controller.paper;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
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
import com.six.ems.entity.tables.TeacherCourse;
import com.six.ems.entity.tables.User;
import com.six.ems.entity.utils.QuestionPaper;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.utils.WebRequestUtil;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.course.CourseServiceImpl;
import com.six.ems.web.service.impl.paper.PaperRuleServiceImpl;
import com.six.ems.web.service.impl.paper.PaperServiceImpl;
import com.six.ems.web.service.impl.questions.BlankServiceImpl;
import com.six.ems.web.service.impl.questions.ChoiceServiceImpl;
import com.six.ems.web.service.impl.questions.JudgeServiceImpl;
import com.six.ems.web.service.impl.questions.SubjectiveServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.UserServiceImpl;
import com.six.ems.web.service.interfaces.users.AdminService;
import com.sun.org.apache.bcel.internal.generic.NEW;

import sun.java2d.d3d.D3DDrawImage;

/**
 * Servlet implementation class PaperServlet
 */
public class PaperServlet extends BaseServlet {


    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PaperServlet() {
        super();
    }

    /**
     * 初始化手动组卷
     *
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    public String initHandPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Course> query = new CourseServiceImpl().courseQuery();
        request.setAttribute("courses", query);

        return "/WEB-INF/jsp/paper/handPaper.jsp";
    }
    

    /**
     * 初始化自动组卷
     */
    public String initAutoPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Course> query = new CourseServiceImpl().courseQuery();
        request.setAttribute("courses", query);
        AdminService adminService = new AdminServiceImpl();
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("choice_type",new SelectItem(0));
        List<Choice> sChoices = adminService.getTypeByCondition(Choice.class,condition);
        condition.put("choice_type",new SelectItem(1));
        List<Choice> mChoices = adminService.getTypeByCondition(Choice.class,condition);
        List<Blank> blanks = adminService.getTypeItems(Blank.class);
        List<Judge> judges = adminService.getTypeItems(Judge.class);
        List<Subjective> subjectives = adminService.getTypeItems(Subjective.class);
        if(CollectionUtils.isNotBlank(sChoices)){
            request.setAttribute("sChoice",sChoices.size());
        }
        if(CollectionUtils.isNotBlank(mChoices)){
            request.setAttribute("mChoice",mChoices.size());
        }
        if(CollectionUtils.isNotBlank(blanks)){
            request.setAttribute("blanks",blanks.size());
        }
        if(CollectionUtils.isNotBlank(judges)){
            request.setAttribute("judges",judges.size());
        }
        if(CollectionUtils.isNotBlank(sChoices)){
            request.setAttribute("sChoice",sChoices.size());
        }
        if(CollectionUtils.isNotBlank(subjectives)){
            request.setAttribute("subjectives",subjectives.size());
        }
        return "/WEB-INF/jsp/paper/autoPaper.jsp";
    }

    /**
     * 获取单选择题
     */
    public String getChoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cId = request.getParameter("courseId");
    	Integer courseId = Integer.valueOf(cId);
    	List<Choice> allChoice = new ChoiceServiceImpl().getAllChoice(0,courseId);

        request.setAttribute("allChoice", allChoice);

        return "/WEB-INF/jsp/paper/handListQuestions.jsp";

    }

    /**
     * 获取多选择题
     */
    public String getMultiple(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cId = request.getParameter("courseId");
    	Integer courseId = Integer.valueOf(cId);
    	List<Choice> allMultiple = new ChoiceServiceImpl().getAllChoice(1,courseId);
        request.setAttribute("allMultiple", allMultiple);

        return "/WEB-INF/jsp/paper/handListQuestions.jsp";

    }

    /**
     * 获取填空题
     */
    public String getBlank(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cId = request.getParameter("courseId");
    	Integer courseId = Integer.valueOf(cId);
    	
    	List<Blank> allBlank = new BlankServiceImpl().getBlankByCourseId(courseId);

        request.setAttribute("allBlank", allBlank);

        return "/WEB-INF/jsp/paper/handListQuestions.jsp";
    }


    /**
     * 获取判断题
     */
    public String getJudge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cId = request.getParameter("courseId");
    	Integer courseId = Integer.valueOf(cId);
    	List<Judge> allJudge = new JudgeServiceImpl().getJudgeByCourseId(courseId);

        request.setAttribute("allJudge", allJudge);

        return "/WEB-INF/jsp/paper/handListQuestions.jsp";

    }


    /**
     * 获取主观题
     */
    public String getSubjective(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String cId = request.getParameter("courseId");
    	Integer courseId = Integer.valueOf(cId);
    	List<Subjective> allSubjective = new SubjectiveServiceImpl().getSubjectiveByCourseId(courseId);

        request.setAttribute("allSubjective", allSubjective);

        return "/WEB-INF/jsp/paper/handListQuestions.jsp";

    }

    /**
     * 将手动组卷内容存储到数据库
     */
    public void saveHandPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取单选题数据
        String choiceIds = request.getParameter("choiceIds");
        // 获取单选题总分数
        String choiceIdsScore = request.getParameter("choiceIdsScore");
        // 获取单选题总数
        String choiceIdsNum = request.getParameter("choiceIdsNum");

        // 获取多选题数据
        String multipleId = request.getParameter("multipleId");
        // 获取多选题总分数
        String multipleIdsScore = request.getParameter("multipleIdsScore");
        // 获取多选题总数
        String multipleIdsNum = request.getParameter("multipleIdsNum");

        // 获取判断题数据
        String blankIds = request.getParameter("blankIds");
        // 获取判断题总分数
        String blankIdsScore = request.getParameter("blankIdsScore");
        // 获取判断题总数
        String blankIdsNum = request.getParameter("blankIdsNum");

        // 获取填空题数据
        String judgeIds = request.getParameter("judgeIds");
        // 获取填空题总分数
        String judgeIdsScore = request.getParameter("judgeIdsScore");
        // 获取填空题总数
        String judgeIdsNum = request.getParameter("judgeIdsNum");

        // 获取主观题数据
        String subjectiveIds = request.getParameter("subjectiveIds");
        // 获取主观题总分数
        String subjectiveIdsScore = request.getParameter("subjectiveIdsScore");
        // 获取主观题总数
        String subjectiveIdsNum = request.getParameter("subjectiveIdsNum");
        
        // 获取班级id
        String courseId = request.getParameter("coursID");
        // 规则名设置
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
        String ruleName = dateFormat.format(date);
        
        // 获取总分
        Integer allScore = Integer.valueOf(choiceIdsScore) + Integer.valueOf(multipleIdsScore) + Integer.valueOf(blankIdsScore)
                + Integer.valueOf(judgeIdsScore) + Integer.valueOf(subjectiveIdsScore);

        PaperRule paperRule = new PaperRule();
        paperRule.setSingleChoiceChecked(choiceIds);
        paperRule.setSingleScore(new BigDecimal(choiceIdsScore));
        paperRule.setSingleChoiceNum(Integer.valueOf(choiceIdsNum));

        paperRule.setMulChoiceChecked(multipleId);
        paperRule.setMulScore(new BigDecimal(multipleIdsScore));
        paperRule.setMulChoiceNum(Integer.valueOf(multipleIdsNum));

        paperRule.setFileBlankChoiceChecked(blankIds);
        paperRule.setFileBlankScore(new BigDecimal(blankIdsScore));
        paperRule.setFileBlankChoiceNum(Integer.valueOf(blankIdsNum));

        paperRule.setJudgeChecked(judgeIds);
        paperRule.setJudgeScore(new BigDecimal(judgeIdsScore));
        paperRule.setJudgeNum(Integer.valueOf(judgeIdsNum));

        paperRule.setSubChoiceChecked(subjectiveIds);
        paperRule.setSubScore(new BigDecimal(subjectiveIdsScore));
        paperRule.setSubChoiceNum(Integer.valueOf(subjectiveIdsNum));

        paperRule.setCourseID(Integer.valueOf(courseId));
        paperRule.setPaperScore(allScore);
        paperRule.setRuleType(Integer.valueOf(1));
        paperRule.setRuleName(ruleName);

        int row = new PaperRuleServiceImpl().addPaperRule(paperRule);
        response.getWriter().print(row != 0 ? "OK" : "NO");

    }

    /**
     * 将自动组信息提交
     */

    public void saveAutoPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cId = request.getParameter("coursID");
        Integer courseId = Integer.valueOf(cId);

        String choiceIdsNum = request.getParameter("choiceIdsNum");
        choiceIdsNum = choiceIdsNum.equals("") ? "0" : choiceIdsNum;
        String choiceIdsScore = request.getParameter("choiceIdsScore");
        choiceIdsScore = choiceIdsScore.equals("") ? "0" : choiceIdsScore;
        String multipleIdsNum = request.getParameter("multipleIdsNum");
        multipleIdsNum = multipleIdsNum.equals("") ? "0" : multipleIdsNum;
        String multipleIdsScore = request.getParameter("multipleIdsScore");
        multipleIdsScore = multipleIdsScore.equals("") ? "0" : multipleIdsScore;
        String blankIdsNum = request.getParameter("blankIdsNum");
        blankIdsNum = blankIdsNum.equals("") ? "0" : blankIdsNum;
        String blankIdsScore = request.getParameter("blankIdsScore");
        blankIdsScore = blankIdsScore.equals("") ? "0" : blankIdsScore;
        String judgeIdsNum = request.getParameter("judgeIdsNum");
        judgeIdsNum = judgeIdsNum.equals("") ? "0" : judgeIdsNum;
        String judgeIdsScore = request.getParameter("judgeIdsScore");
        judgeIdsScore = judgeIdsScore.equals("") ? "0" : judgeIdsScore;
        String subjectiveIdsNum = request.getParameter("subjectiveIdsNum");
        subjectiveIdsNum = subjectiveIdsNum.equals("") ? "0" : subjectiveIdsNum;
        String subjectiveIdsScore = request.getParameter("subjectiveIdsScore");
        subjectiveIdsScore = subjectiveIdsScore.equals("") ? "0" : subjectiveIdsScore;
        // 规则名设置
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm");
        String ruleName = dateFormat.format(date);

        String choiceCk = new PaperRuleServiceImpl().getQuestionsToPaper(choiceIdsNum, choiceIdsScore,courseId, "choice");
        String multipleCk = new PaperRuleServiceImpl().getQuestionsToPaper(multipleIdsNum, multipleIdsScore,courseId, "multiple");
        String blankCk = new PaperRuleServiceImpl().getQuestionsToPaper(blankIdsNum, blankIdsScore,courseId, "blank");
        String judgeCk = new PaperRuleServiceImpl().getQuestionsToPaper(judgeIdsNum, judgeIdsScore,courseId, "judge");
        String subjectiveCk = new PaperRuleServiceImpl().getQuestionsToPaper(subjectiveIdsNum, subjectiveIdsScore,courseId, "subjective");
        Integer allScore = Integer.valueOf(choiceIdsNum) * Integer.valueOf(choiceIdsScore) +
                Integer.valueOf(multipleIdsNum) * Integer.valueOf(multipleIdsScore) +
                Integer.valueOf(blankIdsNum) * Integer.valueOf(blankIdsScore) +
                Integer.valueOf(judgeIdsNum) * Integer.valueOf(judgeIdsScore) +
                Integer.valueOf(subjectiveIdsNum) * Integer.valueOf(subjectiveIdsScore);
        PaperRule paperRule = new PaperRule();
        paperRule.setCourseID(Integer.valueOf(courseId));
        paperRule.setRuleType(Integer.valueOf(0));
        paperRule.setPaperScore(allScore);

        paperRule.setSingleChoiceChecked(choiceCk);
        paperRule.setSingleScore(new BigDecimal(choiceIdsScore));
        paperRule.setSingleChoiceNum(Integer.valueOf(choiceIdsNum));

        paperRule.setMulChoiceChecked(multipleCk);
        paperRule.setMulScore(new BigDecimal(multipleIdsScore));
        paperRule.setMulChoiceNum(Integer.valueOf(multipleIdsNum));

        paperRule.setFileBlankChoiceChecked(blankCk);
        paperRule.setFileBlankScore(new BigDecimal(blankIdsScore));
        paperRule.setFileBlankChoiceNum(Integer.valueOf(blankIdsNum));

        paperRule.setJudgeChecked(judgeCk);
        paperRule.setJudgeScore(new BigDecimal(judgeIdsScore));
        paperRule.setJudgeNum(Integer.valueOf(judgeIdsNum));

        paperRule.setSubChoiceChecked(subjectiveCk);
        paperRule.setSubScore(new BigDecimal(subjectiveIdsScore));
        paperRule.setSubChoiceNum(Integer.valueOf(subjectiveIdsNum));
        
        paperRule.setRuleName(ruleName);

        int row = new PaperRuleServiceImpl().addPaperRule(paperRule);
        response.getWriter().print(row != 0 ? "OK" : "NO");
    }

    /**
     * 初始化试卷列表
     */
    public String initPaperList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "/WEB-INF/jsp/paper/listPapers.jsp";
    }

    /**
     * 获取规则列表
     */
    public void getPaperRuleJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String coursId = request.getParameter("coursId");
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("course_id", new SelectItem(Integer.valueOf(coursId)));
        List<PaperRule> paperRules = new PaperRuleServiceImpl().getPaperRuleByCondition(condition);
        List<Map<String, Object>> paperList = new ArrayList<>();
        String json = null;
        if (paperRules != null) {
            for (PaperRule paperRule : paperRules) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", paperRule.getRuleID());
                map.put("name", paperRule.getRuleName());
                paperList.add(map);
            }
            json = JSON.toJSONString(paperList);
        }
        response.getWriter().print(json == null ? "[{}]" : json);
    }
    
    /**
     * 根据课程id获取paper
     */
    public void getPaperJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String coursId = request.getParameter("coursId");
        List<Paper> papers = new PaperServiceImpl().getPaperByCourseId(Integer.valueOf(coursId));
        List<Map<String, Object>> paperList = new ArrayList<>();
        String json = null;
        if (papers != null) {
            for (Paper paper : papers) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", paper.getPaperID());
                map.put("name", paper.getPaperName());
                paperList.add(map);
            }
            json = JSON.toJSONString(paperList);
        }
        response.getWriter().print(json == null ? "[{}]" : json);
    }

    /**
     * 加载试卷列表
     */
    public void jsonPaperList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	AdminService adminS = new AdminServiceImpl();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        User user = new UserServiceImpl().getUserByUserId(userId);
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("tea_id", new SelectItem(user.getTeaId()));
        List<TeacherCourse> teacherCourses = adminS.getTypeByCondition(TeacherCourse.class, condition);
        condition.clear();
        List<Paper> papers = new ArrayList<>();
        for(TeacherCourse teacherCourse : teacherCourses) {
        	Integer corseId = teacherCourse.getCourseId();
        	condition.put("course_id", new SelectItem(corseId));
        	List<Paper> paper = adminS.getTypeByCondition(Paper.class, condition);
        	if(CollectionUtils.isNotBlank(paper)) {
        		papers.addAll(paper);
        	}
        }
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
        int begin = (page-1)*pageSize;
        int end = page*pageSize;
        int size = papers.size();
        List<Paper> paper = papers.subList(begin, end >= size ? size : end);
        String json = CollectionUtils.creatDataGritJson(paper, size);
        response.getWriter().print(json);
    }

    /**
     * 删除试卷
     */
    public void deletePaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
    	String paperID = request.getParameter("paperId");
        String[] paperIds = paperID.split(",");
        int rows = 0;
        for (String pId : paperIds) {
            Integer paperId = Integer.valueOf(pId);
            /*int row = new PaperServiceImpl().deletePaper(paperId);
            if (row > 0) {
                rows++;
            }*/
            boolean b = adminServiceImpl.delete(new Paper(paperId));
            if(b) {
            	rows ++;
            }
        }
        if (rows == paperIds.length) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }
    }

    /**
     * 添加试卷信息
     */
    public void addPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Paper paper = WebRequestUtil.parseObject(Paper.class, request);
        paper.setAvailable("1");
        int row = new PaperServiceImpl().addPaper(paper);

        if (row > 0) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }
    }

    /**
     * 修改试卷信息
     */
    public void updatePaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Paper paper = WebRequestUtil.parseObject(Paper.class, request);
        int row = new PaperServiceImpl().updatePaper(paper);

        if (row > 0) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }

    }

    /**
     * 初始化查看试卷
     */
    public String initPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String paperId = request.getParameter("paperId");
        Paper paper = new PaperServiceImpl().getPaperById(Integer.valueOf(paperId));
        PaperRule paperRule = new PaperRuleServiceImpl().getPaperRuleByRuleId(paper.getRuleID());
        // 单选题
        List<Choice> choices = new ArrayList<>();
        String singleChoiceChecked = paperRule.getSingleChoiceChecked();
        Integer singleChoiceNum = paperRule.getSingleChoiceNum();
        BigDecimal singleScore = paperRule.getSingleScore();
        List<QuestionPaper> choiceQues = JSONArray.parseArray(singleChoiceChecked, QuestionPaper.class);
        if(!singleChoiceChecked.equals("") && singleChoiceNum != 0 && singleScore.doubleValue() != 0) {
        for (QuestionPaper choiceQue : choiceQues) {
            Map<String, SelectItem> condition = new HashMap<>();
            condition.put("choice_id", new SelectItem(choiceQue.getId()));
            List<Choice> query = AdvanceUtil.select(Choice.class, condition);
            if (CollectionUtils.isNotBlank(query)) {
                choices.add(query.get(0));
            }
        }
        request.setAttribute("choices", choices);                  // 单选择题内容
        request.setAttribute("choiceQues", choiceQues);            // 单选题单个分数
        request.setAttribute("singleChoiceNum", singleChoiceNum);  // 单选题总个数
        if(paperRule.getRuleType() == 0) {
        	int singS = (int) (singleChoiceNum * choiceQues.get(0).getScore());
        	request.setAttribute("singleScore", singS);          // 单选题总分数
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
        if(!mulChoiceChecked.equals("") && mulChoiceNum != 0 && mulScore.doubleValue() != 0) {
        for (QuestionPaper questionPaper : mulChoiceQues) {
            Map<String, SelectItem> condition = new HashMap<>();
            condition.put("choice_id", new SelectItem(questionPaper.getId()));
            List<Choice> query = AdvanceUtil.select(Choice.class, condition);
            if (CollectionUtils.isNotBlank(query)) {
                mulChoices.add(query.get(0));
            }
        }
        request.setAttribute("mulChoices", mulChoices);
        request.setAttribute("mulChoiceQues", mulChoiceQues);
        request.setAttribute("mulChoiceNum", mulChoiceNum);
        
        if(paperRule.getRuleType() == 0) {
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
        if(!judgeChoiceChecked.equals("") && judgeChoiceNum != 0 && judgeScore.doubleValue() != 0) {
        for (QuestionPaper judgeQue : judgeQues) {
            Map<String, SelectItem> condition = new HashMap<>();
            condition.put("judge_id", new SelectItem(judgeQue.getId()));
            List<Judge> query = AdvanceUtil.select(Judge.class, condition);
            if (CollectionUtils.isNotBlank(query)) {
                judges.add(query.get(0));
            }
        }
        request.setAttribute("judges", judges);
        request.setAttribute("judgeQues", judgeQues);
        request.setAttribute("judgeChoiceNum", judgeChoiceNum);
        
        if(paperRule.getRuleType() == 0) {
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
        if(!blankChoiceChecked.equals("") && blankChoiceNum != 0 && blankScore.doubleValue() != 0) {
        for (QuestionPaper blankQue : blankQues) {
            Map<String, SelectItem> condition = new HashMap<>();
            condition.put("blank_id", new SelectItem(blankQue.getId()));
            List<Blank> query = AdvanceUtil.select(Blank.class, condition);
            if (CollectionUtils.isNotBlank(query)) {
                blanks.add(query.get(0));
            }
        }
        request.setAttribute("blanks", blanks);
        request.setAttribute("blankQues", blankQues);
        request.setAttribute("blankChoiceNum", blankChoiceNum);
        
        if(paperRule.getRuleType() == 0) {
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
        if(!subChoiceChecked.equals("") && subChoiceNum != 0 && subScore.doubleValue() != 0) {
        	for (QuestionPaper subQue : subQues) {
	            Map<String, SelectItem> condition = new HashMap<>();
	            condition.put("sub_id", new SelectItem(subQue.getId()));
	            List<Subjective> query = AdvanceUtil.select(Subjective.class, condition);
	            if (CollectionUtils.isNotBlank(query)) {
	                subjectives.add(query.get(0));
	            }
	        }
	        request.setAttribute("subjectives", subjectives);
	        request.setAttribute("subQues", subQues);
	        request.setAttribute("subChoiceNum", subChoiceNum);
	        
	        if(paperRule.getRuleType() == 0) {
	        	int subS = (int) (subChoiceNum * subQues.get(0).getScore());
	        	request.setAttribute("subScore", subS);    
	        } else {
	        	request.setAttribute("subScore", subScore);
	        }
        }


        return "/WEB-INF/jsp/paper/showPaper.jsp";
    }

    /**
     * 初始化查看规则
     */
    public String initShowPaperRule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        return "/WEB-INF/jsp/paper/listRules.jsp";
    }

    /**
     * 获取规则列表
     */
    public void showPaperRule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	AdminService adminS = new AdminServiceImpl();
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        User user = new UserServiceImpl().getUserByUserId(userId);
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("tea_id", new SelectItem(user.getTeaId()));
        List<TeacherCourse> teacherCourses = adminS.getTypeByCondition(TeacherCourse.class, condition);
        condition.clear();
        List<PaperRule> paperRules = new ArrayList<>();
        for(TeacherCourse teacherCourse : teacherCourses) {
        	Integer corseId = teacherCourse.getCourseId();
        	condition.put("course_id", new SelectItem(corseId));
        	List<PaperRule> paperRule = adminS.getTypeByCondition(PaperRule.class, condition);
        	if(CollectionUtils.isNotBlank(paperRule)) {
        		paperRules.addAll(paperRule);
        	}
        }
        
        Integer page = Integer.valueOf(request.getParameter("page"));
        Integer pageSize = Integer.valueOf(request.getParameter("rows"));
        
        int begin = (page-1)*pageSize;
        int end = page*pageSize;
        int size = paperRules.size();
        List<PaperRule> pagePaperRule = paperRules.subList(begin, end >= size ? size : end);
        String json = CollectionUtils.creatDataGritJson(pagePaperRule, paperRules.size());

        response.getWriter().print(json);
    }

    /**
     * 删除规则列表
     */
    public void deletePaperRule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AdminServiceImpl adminServiceImpl = new AdminServiceImpl();
    	String ruleId = request.getParameter("stuId");
        String[] ruleIds = ruleId.split(",");
        Integer row = 0;
        if (ruleIds.length > 0) {
            for (String rID : ruleIds) {
                Integer rId = Integer.valueOf(rID);
                boolean b = adminServiceImpl.delete(new PaperRule(rId));
                if(b) {
                	row ++;
                }
                // row += new PaperRuleServiceImpl().deletePaperRule(new PaperRule(rId));
            }
        }
        if (row == ruleIds.length) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }
    }

    /**
     * 修改规则列表
     */
    public void updatePaperRule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PaperRule paperRule = WebRequestUtil.parseObject(PaperRule.class, request);
        Integer row = new PaperRuleServiceImpl().updatePaperRule(paperRule);
        if (row > 0) {
            response.getWriter().print("OK");
        } else {
            response.getWriter().print("NO");
        }
    }
    
    /**
     * 根据规则id获取规则名
     */
    public void getPaperRuleNameByRuleId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer ruleId = Integer.valueOf(request.getParameter("ruleId"));
    	PaperRule paperRule = new PaperRuleServiceImpl().getPaperRuleByRuleId(ruleId);
    	
    	response.getWriter().print(paperRule.getRuleName());
    }
    
    /**
     * 根据规则id获取规则json
     */
    public void getPaperRuleJsonByRuleId(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	Integer ruleId = Integer.valueOf(request.getParameter("ruleId"));
    	PaperRule paperRule = new PaperRuleServiceImpl().getPaperRuleByRuleId(ruleId);
    	String jsonString = "";
    	if(paperRule != null) {
    		jsonString = JSON.toJSONString(paperRule);
    	} else {
    		jsonString = "";
    	}
    	
    	response.getWriter().print(jsonString);
    }
    
    /**
     * 修改手动提交
     */
    public void updateHandRule(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	// 获取规则id
    	Integer ruleId = Integer.valueOf(request.getParameter("ruleId"));
    	// 获取单选题数据
        String choiceIds = request.getParameter("choiceIds");
        // 获取单选题总分数
        String choiceIdsScore = request.getParameter("choiceIdsScore");
        // 获取单选题总数
        String choiceIdsNum = request.getParameter("choiceIdsNum");

        // 获取多选题数据
        String multipleId = request.getParameter("multipleId");
        // 获取多选题总分数
        String multipleIdsScore = request.getParameter("multipleIdsScore");
        // 获取多选题总数
        String multipleIdsNum = request.getParameter("multipleIdsNum");

        // 获取判断题数据
        String blankIds = request.getParameter("blankIds");
        // 获取判断题总分数
        String blankIdsScore = request.getParameter("blankIdsScore");
        // 获取判断题总数
        String blankIdsNum = request.getParameter("blankIdsNum");

        // 获取填空题数据
        String judgeIds = request.getParameter("judgeIds");
        // 获取填空题总分数
        String judgeIdsScore = request.getParameter("judgeIdsScore");
        // 获取填空题总数
        String judgeIdsNum = request.getParameter("judgeIdsNum");

        // 获取主观题数据
        String subjectiveIds = request.getParameter("subjectiveIds");
        // 获取主观题总分数
        String subjectiveIdsScore = request.getParameter("subjectiveIdsScore");
        // 获取主观题总数
        String subjectiveIdsNum = request.getParameter("subjectiveIdsNum");
        
        // 获取班级id
        String courseId = request.getParameter("coursID");
        // 规则名设置
        String ruleName = request.getParameter("ruleName");
        
        // 获取总分
        Integer allScore = Integer.valueOf(choiceIdsScore) + Integer.valueOf(multipleIdsScore) + Integer.valueOf(blankIdsScore)
                + Integer.valueOf(judgeIdsScore) + Integer.valueOf(subjectiveIdsScore);

        PaperRule paperRule = new PaperRule();
        paperRule.setSingleChoiceChecked(choiceIds);
        paperRule.setSingleScore(new BigDecimal(choiceIdsScore));
        paperRule.setSingleChoiceNum(Integer.valueOf(choiceIdsNum));

        paperRule.setMulChoiceChecked(multipleId);
        paperRule.setMulScore(new BigDecimal(multipleIdsScore));
        paperRule.setMulChoiceNum(Integer.valueOf(multipleIdsNum));

        paperRule.setFileBlankChoiceChecked(blankIds);
        paperRule.setFileBlankScore(new BigDecimal(blankIdsScore));
        paperRule.setFileBlankChoiceNum(Integer.valueOf(blankIdsNum));

        paperRule.setJudgeChecked(judgeIds);
        paperRule.setJudgeScore(new BigDecimal(judgeIdsScore));
        paperRule.setJudgeNum(Integer.valueOf(judgeIdsNum));

        paperRule.setSubChoiceChecked(subjectiveIds);
        paperRule.setSubScore(new BigDecimal(subjectiveIdsScore));
        paperRule.setSubChoiceNum(Integer.valueOf(subjectiveIdsNum));

        paperRule.setCourseID(Integer.valueOf(courseId));
        paperRule.setPaperScore(allScore);
        paperRule.setRuleType(Integer.valueOf(1));
        paperRule.setRuleName(ruleName);
        paperRule.setRuleID(ruleId);

        int row = new PaperRuleServiceImpl().updatePaperRule(paperRule);
        response.getWriter().print(row != 0 ? "OK" : "NO");
    }
    
    /**
     * 将自动组信息修改
     */

    public void updateAutoPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cId = request.getParameter("coursID");
        Integer courseId = Integer.valueOf(cId);
        
        String choiceIdsNum = request.getParameter("choiceIdsNum");
        String choiceIdsScore = request.getParameter("choiceIdsScore");
        String multipleIdsNum = request.getParameter("multipleIdsNum");
        String multipleIdsScore = request.getParameter("multipleIdsScore");
        String blankIdsNum = request.getParameter("blankIdsNum");
        String blankIdsScore = request.getParameter("blankIdsScore");
        String judgeIdsNum = request.getParameter("judgeIdsNum");
        String judgeIdsScore = request.getParameter("judgeIdsScore");
        String subjectiveIdsNum = request.getParameter("subjectiveIdsNum");
        String subjectiveIdsScore = request.getParameter("subjectiveIdsScore");
        // 规则名设置
        String ruleName = request.getParameter("ruleName");
        // 获取规则id
        Integer ruleId = Integer.valueOf(request.getParameter("ruleId"));
        
        String choiceCk = new PaperRuleServiceImpl().getQuestionsToPaper(choiceIdsNum, choiceIdsScore,courseId, "choice");
        String multipleCk = new PaperRuleServiceImpl().getQuestionsToPaper(multipleIdsNum, multipleIdsScore,courseId, "multiple");
        String blankCk = new PaperRuleServiceImpl().getQuestionsToPaper(blankIdsNum, blankIdsScore,courseId, "blank");
        String judgeCk = new PaperRuleServiceImpl().getQuestionsToPaper(judgeIdsNum, judgeIdsScore,courseId, "judge");
        String subjectiveCk = new PaperRuleServiceImpl().getQuestionsToPaper(subjectiveIdsNum, subjectiveIdsScore,courseId, "subjective");
        Integer allScore = Integer.valueOf(choiceIdsNum) * Integer.valueOf(choiceIdsScore) +
                Integer.valueOf(multipleIdsNum) * Integer.valueOf(multipleIdsScore) +
                Integer.valueOf(blankIdsNum) * Integer.valueOf(blankIdsScore) +
                Integer.valueOf(judgeIdsNum) * Integer.valueOf(judgeIdsScore) +
                Integer.valueOf(subjectiveIdsNum) * Integer.valueOf(subjectiveIdsScore);
        PaperRule paperRule = new PaperRule();
        paperRule.setCourseID(Integer.valueOf(courseId));
        paperRule.setRuleType(Integer.valueOf(0));
        paperRule.setPaperScore(allScore);

        paperRule.setSingleChoiceChecked(choiceCk);
        paperRule.setSingleScore(new BigDecimal(choiceIdsScore));
        paperRule.setSingleChoiceNum(Integer.valueOf(choiceIdsNum));

        paperRule.setMulChoiceChecked(multipleCk);
        paperRule.setMulScore(new BigDecimal(multipleIdsScore));
        paperRule.setMulChoiceNum(Integer.valueOf(multipleIdsNum));

        paperRule.setFileBlankChoiceChecked(blankCk);
        paperRule.setFileBlankScore(new BigDecimal(blankIdsScore));
        paperRule.setFileBlankChoiceNum(Integer.valueOf(blankIdsNum));

        paperRule.setJudgeChecked(judgeCk);
        paperRule.setJudgeScore(new BigDecimal(judgeIdsScore));
        paperRule.setJudgeNum(Integer.valueOf(judgeIdsNum));

        paperRule.setSubChoiceChecked(subjectiveCk);
        paperRule.setSubScore(new BigDecimal(subjectiveIdsScore));
        paperRule.setSubChoiceNum(Integer.valueOf(subjectiveIdsNum));
        
        paperRule.setRuleName(ruleName);
        paperRule.setRuleID(ruleId);

        int row = new PaperRuleServiceImpl().updatePaperRule(paperRule);
        response.getWriter().print(row != 0 ? "OK" : "NO");
    }
}
