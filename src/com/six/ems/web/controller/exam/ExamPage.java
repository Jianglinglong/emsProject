package com.six.ems.web.controller.exam;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.entity.tables.Choice;
import com.six.ems.entity.tables.Paper;
import com.six.ems.entity.tables.PaperRule;
import com.six.ems.entity.utils.QuestionPaper;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.controller.base.BaseServlet;
import com.six.ems.web.service.impl.paper.PaperRuleServiceImpl;
import com.six.ems.web.service.impl.paper.PaperServiceImpl;

/**
 * Servlet implementation class ExamPage
 */
public class ExamPage extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ExamPage() {
        super();
    }
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public String initPaper(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String paperId = request.getParameter("paperId");
//		System.out.println(paperId);
 		Paper paper = new PaperServiceImpl().getPaperById(Integer.valueOf(paperId));
 		PaperRule paperRule = new PaperRuleServiceImpl().getPaperRuleByRuleId(paper.getRuleID());
 		// 单选题
 		List<Choice> choices = new ArrayList<>();
 		String singleChoiceChecked = paperRule.getSingleChoiceChecked();
 		Integer singleChoiceNum = paperRule.getSingleChoiceNum();
 		BigDecimal singleScore = paperRule.getSingleScore();
 		List<QuestionPaper> choiceQues = JSONArray.parseArray(singleChoiceChecked, QuestionPaper.class);
 		for(QuestionPaper choiceQue : choiceQues) {
 			Map< String, SelectItem> condition = new HashMap<>();
 	 		condition.put("choice_id", new SelectItem(choiceQue.getId()));
 	 		List<Choice> list = AdvanceUtil.select(Choice.class, condition);
 	 		if(CollectionUtils.isNotBlank(list)) {
 	 			choices.add(list.get(0));
 	 		}
 		}
 		request.setAttribute("choices", choices);                  // 单选择题内容
 		request.setAttribute("choiceQues", choiceQues);            // 单选题单个分数
 		request.setAttribute("singleChoiceNum", singleChoiceNum);  // 单选题总个数
 		request.setAttribute("singleScore", singleScore);          // 单选题总分数
 		
 		// 多选题
 		List<Choice> mulChoices = new ArrayList<>();
 		String mulChoiceChecked = paperRule.getMulChoiceChecked();
 		
 		
 		return "/WEB-INF/jsp/exam/examPage.jsp";
	}
}
