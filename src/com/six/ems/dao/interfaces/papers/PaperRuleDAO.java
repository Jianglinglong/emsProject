package com.six.ems.dao.interfaces.papers;

import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.PaperRule;

public interface PaperRuleDAO {
	/**
	 * 存储试卷
	 */
	int addPaperRule(PaperRule paperRule);

	/**
	 * 根据规则id获取规则
	 * @param ruleID
	 * @return
	 */
    PaperRule getPaerRuleByID(Integer ruleID);
    
    /**
     * 获取所有规则
     */
    List<PaperRule> getPaperRule();
    
    /**
     * 分页获取所有规则
     */
    List<PaperRule> getPagePaperRule(Integer pageSize, Integer page);
    List<PaperRule> getPagePaperRule(Map<String,SelectItem> condition, Integer pageSize, Integer page);

    /**
	 * 修改规则
	 */
	Integer updatePaperRule(PaperRule paperRule);
	
	/**
	 * 删除规则
	 */
	Integer deletePaperRule(PaperRule paperRule);
	
	/**
	 * 根据条件获取规则
	 */
	List<PaperRule> getPaperRuleByCondition(Map<String, SelectItem> condition);
}
