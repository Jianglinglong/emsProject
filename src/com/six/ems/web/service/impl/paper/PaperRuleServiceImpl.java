package com.six.ems.web.service.impl.paper;

import java.util.Collections;
import java.util.List;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.six.ems.dao.impl.papers.PaperRuleDAOImpl;
import com.six.ems.dao.impl.questions.BlankDAOImpl;
import com.six.ems.dao.impl.questions.ChoiceDAOImpl;
import com.six.ems.dao.impl.questions.JudgeDAOImpl;
import com.six.ems.dao.impl.questions.SubjectiveDAOImpl;
import com.six.ems.dao.interfaces.papers.PaperRuleDAO;
import com.six.ems.entity.tables.Blank;
import com.six.ems.entity.tables.Choice;
import com.six.ems.entity.tables.Judge;
import com.six.ems.entity.tables.PaperRule;
import com.six.ems.entity.tables.Subjective;
import com.six.ems.web.service.impl.questions.ChoiceServiceImpl;
import com.six.ems.web.service.interfaces.paper.PaperRuleService;

public class PaperRuleServiceImpl implements PaperRuleService {
	private PaperRuleDAO paperRuleDAO = new PaperRuleDAOImpl();
	
	@Override
	public int addPaperRule(PaperRule paperRule) {
		
		return paperRuleDAO.addPaperRule(paperRule);
	}

	@Override
	public String getQuestionsToPaper(String nums, String scores,Integer courseId, String questionType) {
		Integer num = Integer.valueOf(nums);
		Integer score = Integer.valueOf(scores);
		if(num > 0) {
			String json ="";
			if(questionType.equals("choice")) {
				List<Choice> allChoice = new ChoiceDAOImpl().getAllChoice(0,courseId);
				json = "[";
				Collections.shuffle(allChoice);
				for(int i = 0; i < num; i++) {
					json += "{'id':"+allChoice.get(i).getChoiceId();
					json += ",'score':"+score+"},";
				}
				json = json.substring(0, json.length()-1);
				json += "]";
			} else if(questionType.equals("multiple")) {
				List<Choice> allMultiple = new ChoiceServiceImpl().getAllChoice(1,courseId);
				json = "[";
				Collections.shuffle(allMultiple);
				for(int i = 0; i < num; i++) {
					json += "{'id':"+allMultiple.get(i).getChoiceId();
					json += ",'score':"+score+"},";
				}
				json = json.substring(0, json.length()-1);
				json += "]";
			} else if(questionType.equals("blank")) {
				List<Blank> allBlank = new BlankDAOImpl().getBlankByCourseId(courseId);
				json = "[";
				Collections.shuffle(allBlank);
				for(int i = 0; i < num; i++) {
					json += "{'id':"+allBlank.get(i).getBlankId();
					json += ",'score':"+score+"},";
				}
				json = json.substring(0, json.length()-1);
				json += "]";
			} else if(questionType.equals("judge")) {
				List<Judge> allJudge = new JudgeDAOImpl().getJudgeByCourseId(courseId);
				json = "[";
				Collections.shuffle(allJudge);
				for(int i = 0; i < num; i++) {
					json += "{'id':"+allJudge.get(i).getJudgeId();
					json += ",'score':"+score+"},";
				}
				json = json.substring(0, json.length()-1);
				json += "]";
			} else if(questionType.equals("subjective")) {
				List<Subjective> allSubjective = new SubjectiveDAOImpl().getSubjectiveByCourseId(courseId);
				json = "[";
				Collections.shuffle(allSubjective);
				for(int i = 0; i < num; i++) {
					json += "{'id':"+allSubjective.get(i).getSubId();
					json += ",'score':"+score+"},";
				}
				json = json.substring(0, json.length()-1);
				json += "]";
			}
			
			return json;
		} else {
			return "";
		}
	}

	@Override
	public PaperRule getPaperRuleByRuleId(Integer ruleId) {

		return paperRuleDAO.getPaerRuleByID(ruleId);
	}

	@Override
	public List<PaperRule> getPaperRule() {

		return paperRuleDAO.getPaperRule();
	}

	@Override
	public Integer updatePaperRule(PaperRule paperRule) {

		return paperRuleDAO.updatePaperRule(paperRule);
	}

	@Override
	public Integer deletePaperRule(PaperRule paperRule) {

		return paperRuleDAO.deletePaperRule(paperRule);
	}

	@Override
	public List<PaperRule> getPagePaperRule(Integer pageSize, Integer page) {

		return paperRuleDAO.getPagePaperRule(pageSize, page);
	}

	@Override
	public List<PaperRule> getPaperRuleByCondition(Map<String, SelectItem> condition) {

		return paperRuleDAO.getPaperRuleByCondition(condition);
	}

}
