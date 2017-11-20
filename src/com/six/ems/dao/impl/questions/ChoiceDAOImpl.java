package com.six.ems.dao.impl.questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.questions.ChoiceDAO;
import com.six.ems.entity.tables.Choice;
import com.six.ems.utils.CollectionUtils;

public class ChoiceDAOImpl implements ChoiceDAO {
    /**
     * 查询所有选择题区分单选多选
     */
    @Override
    public List<Choice> getAllChoice(Integer choiceType, Integer courseId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("choice_type", new SelectItem(choiceType));
        condition.put("course_id", new SelectItem(courseId));
        condition.put("available", new SelectItem(1));
        List<Choice> query = AdvanceUtil.select(Choice.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public Integer deleteChoice(Integer choiceId) {
        Choice choice = new Choice();
        choice.setDaytime(null);
        choice.setChoiceId(choiceId);
        int row = AdvanceUtil.delete(choice);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer addChoice(Choice choice) {
        int row = AdvanceUtil.insert(choice);

        return row > 0 ? row : 0;
    }

    @Override
    public Integer updateChoice(Choice choice) {
        int row = AdvanceUtil.update(choice);

        return row > 0 ? row : 0;
    }

    @Override
    public Choice getChoiceByID(Integer choiceID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("choice_id", new SelectItem(choiceID));
        condition.put("available",new SelectItem(1));
        List<Choice> choices = AdvanceUtil.select(Choice.class, condition);
        return CollectionUtils.isNotBlank(choices) ? choices.get(0) : null;
    }

    @Override
    public List<Choice> getAllChoice() {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("available",new SelectItem(1));
        List<Choice> query = AdvanceUtil.select(Choice.class,condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public Choice getChoiceByChoiceId(Integer choiceId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("choice_id", new SelectItem(choiceId));
        condition.put("available",new SelectItem(1));
        List<Choice> query = AdvanceUtil.select(Choice.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public List<Choice> choiceQuery(int page, int rows) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return choiceQuery(condition,page,rows);
    }

    @Override
    public List<Choice> choiceQuery(Map<String, SelectItem> condition, int page, int rows) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Choice.class,condition,page,rows);
    }

    @Override
    public int total() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        List<Choice> choices = AdvanceUtil.select(Choice.class, condition);
        return choices!=null? choices.size():0;
    }

}
