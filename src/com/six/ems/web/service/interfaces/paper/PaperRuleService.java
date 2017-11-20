package com.six.ems.web.service.interfaces.paper;

import java.util.List;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.PaperRule;

public interface PaperRuleService {
	/**
	 * 将数据存入
	 */
	int addPaperRule(PaperRule paperRule);
	
	/**
	 * 根据id获取规则
	 */
	PaperRule getPaperRuleByRuleId(Integer ruleId);
	
	/**
	 * 自动组卷获取题目
	 */
	String getQuestionsToPaper(String num, String score, Integer courseId, String questionType);
	
	/**
	 * 获取所有规则
	 */
	List<PaperRule> getPaperRule();
	
	/**
	 * 分页获取规则
	 */
	List<PaperRule> getPagePaperRule(Integer pageSize, Integer page);
	
	/**
	 * 修改规则
	 */
	Integer updatePaperRule(PaperRule paperRule);
	
	/**
	 * 删除规则
	 */
	Integer deletePaperRule(PaperRule paperRule);
	
	/**
	 * 根据条件查找规则
	 */
	List<PaperRule> getPaperRuleByCondition(Map<String, SelectItem> condition);
}
