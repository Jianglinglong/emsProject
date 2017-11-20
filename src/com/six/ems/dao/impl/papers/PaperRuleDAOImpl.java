package com.six.ems.dao.impl.papers;

import java.util.List;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.papers.PaperRuleDAO;
import com.six.ems.entity.tables.PaperRule;
import com.six.ems.utils.CollectionUtils;

public class PaperRuleDAOImpl implements PaperRuleDAO {

    @Override
    public int addPaperRule(PaperRule paperRule) {
        int row = AdvanceUtil.insert(paperRule);
        return row > 0 ? row : 0;
    }

    @Override
    public PaperRule getPaerRuleByID(Integer ruleID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("rule_id", new SelectItem(ruleID));
        //condition.put("available", new SelectItem(1));
        List<PaperRule> paperRule = AdvanceUtil.select(PaperRule.class, condition);
        return CollectionUtils.isNotBlank(paperRule) ? paperRule.get(0) : null;
    }

    @Override
    public List<PaperRule> getPaperRule() {
//        List<PaperRule> query = AdvanceUtil.select(PaperRule.class);
//        return CollectionUtils.isNotBlank(query) ? query : null;
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return getPaperRuleByCondition(condition);
    }

    @Override
    public Integer updatePaperRule(PaperRule paperRule) {
        int row = AdvanceUtil.update(paperRule);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer deletePaperRule(PaperRule paperRule) {
        int row = AdvanceUtil.delete(paperRule);
        return row > 0 ? row : 0;
    }

    @Override
    public List<PaperRule> getPagePaperRule(Integer pageSize, Integer page) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return getPagePaperRule(condition,pageSize,page);
    }

    @Override
    public List<PaperRule> getPagePaperRule(Map<String, SelectItem> condition, Integer pageSize, Integer page) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(PaperRule.class,condition,page,pageSize);
    }

    @Override
    public List<PaperRule> getPaperRuleByCondition(Map<String, SelectItem> condition) {
        condition.put("available",new SelectItem(1));
        List<PaperRule> query = AdvanceUtil.select(PaperRule.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

}
