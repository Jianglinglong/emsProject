package com.six.ems.web.controller.paper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.consts.SQLOperator;
import com.jll.jdbc.tools.AdvanceUtil;
import com.jll.jdbc.tools.BaseUtil;
import com.six.ems.entity.tables.*;
import com.six.ems.entity.utils.QuestionAnswer;
import com.six.ems.entity.utils.QuestionPaper;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.paper.PaperRuleServiceImpl;
import com.six.ems.web.service.impl.paper.PaperServiceImpl;
import com.six.ems.web.service.impl.questions.SubjectiveServiceImpl;
import com.six.ems.web.service.impl.score.ScoreServiceImpl;
import com.six.ems.web.service.impl.users.AdminServiceImpl;
import com.six.ems.web.service.impl.users.StudentServiceImpl;
import com.six.ems.web.service.impl.users.TeacherServiceImpl;
import com.six.ems.web.service.interfaces.questions.SubjectiveService;
import com.six.ems.web.service.interfaces.users.AdminService;

import javax.security.auth.Subject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadPaperServlet extends BaseServlet {
    public String initReadPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/WEB-INF/jsp/teacher/read/readpaper.jsp";
    }
    public String initReadPaperAging(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        return "/WEB-INF/jsp/teacher/read/readpaperaging.jsp";
    }
    public void getAnswerPapaers(HttpServletRequest request,HttpServletResponse response)throws ServletException,IOException{
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("read_flag",new SelectItem("N"));

        List<Answer> answerList = new AdminServiceImpl().getTypeByCondition(Answer.class, condition);
        response.getWriter().print(CollectionUtils.creatDataGritJson(answerList,answerList.size()));
    }

    public void getReadedPapaers(HttpServletRequest request ,HttpServletResponse response) throws ServletException,IOException{
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("read_flag",new SelectItem("Y"));

        List<Answer> answerList = new AdminServiceImpl().getTypeByCondition(Answer.class, condition);
        response.getWriter().print(CollectionUtils.creatDataGritJson(answerList,answerList.size()));
    }

    public String getAnswerInfo(HttpServletRequest request , HttpServletResponse response) throws ServletException,IOException{
        String anwserID = request.getParameter("anwserID");
        Answer answer = new TeacherServiceImpl().getAnswerByAnswerID(Integer.valueOf(anwserID));
        request.setAttribute("answer",answer);
        Integer paperId = answer.getPaperId();
        Score scoreIdByStuId = new ScoreServiceImpl().getScoreByStuId(answer.getStuId(), paperId);
        request.setAttribute("stuScore",scoreIdByStuId);

        Student student = new StudentServiceImpl().getStudentById(answer.getStuId());
        request.setAttribute("student",student.getStuRealName());
        //获取试卷规则
        Paper paperById = new PaperServiceImpl().getPaperById(paperId);
        request.setAttribute("paperName",paperById.getPaperName());
        PaperRule paperRuleByRuleId = new PaperRuleServiceImpl().getPaperRuleByRuleId(paperById.getRuleID());
        String subChoiceChecked = paperRuleByRuleId.getSubChoiceChecked();
        //将规则转为 id score 对象 list集合
        List<QuestionPaper> questionPapers = JSONArray.parseArray(subChoiceChecked, QuestionPaper.class);
        //转为 id score map集合
        Map<Integer, Double> questionPaper = CollectionUtils.parseQuestionPaper(questionPapers);

        String subjectiveJson = answer.getSubId();
        List<QuestionAnswer> questionAnswers = JSONArray.parseArray(subjectiveJson, QuestionAnswer.class);
        SubjectiveService subjectiveService = new SubjectiveServiceImpl();
        for (QuestionAnswer stuAswer : questionAnswers){
            Subjective subjective = new SubjectiveServiceImpl().getSubjectiveBySubId(stuAswer.getId());
            stuAswer.setTitle(subjective.getSubTitle());
            Subjective subjectiveBySubId = subjectiveService.getSubjectiveBySubId(stuAswer.getId());
            stuAswer.setAnswer(subjectiveBySubId.getAnswer());
            stuAswer.setScore(questionPaper.get(subjectiveBySubId.getSubId()));
        }
        request.setAttribute("stuSubAnswer", questionAnswers);
        return "/WEB-INF/jsp/teacher/read/anwser.jsp";
    }
    public void doReadPaper(HttpServletRequest request ,HttpServletResponse response)throws ServletException,IOException{
        String scoreId = request.getParameter("scoreId");
        String answerId = request.getParameter("answerId");
        String autoScore = request.getParameter("autoScore");
        Answer answerByAnswerID = new TeacherServiceImpl().getAnswerByAnswerID(Integer.valueOf(answerId));
        answerByAnswerID.setFlag("Y");
        new AdminServiceImpl().update(answerByAnswerID);
        List<QuestionAnswer> questionAnswers = JSONArray.parseArray(answerByAnswerID.getSubId(), QuestionAnswer.class);
        double score = 0;
        for (QuestionAnswer questionAnswer : questionAnswers){
            Integer id = questionAnswer.getId();
            String scores = request.getParameter(String.valueOf(id));
            score += Double.parseDouble(scores);
        }
        ScoreServiceImpl scoreService = new ScoreServiceImpl();
        Score scoreByScoreId = scoreService.getScoreByScoreId(Integer.valueOf(scoreId));
        scoreByScoreId.setSubScore(score);
        scoreByScoreId.setTotalScore(scoreByScoreId.getAutoScore()+score);
        int update = scoreService.scoreUpdate(scoreByScoreId);
        response.getWriter().print(update>0? "OK":"NO");
    }
}
