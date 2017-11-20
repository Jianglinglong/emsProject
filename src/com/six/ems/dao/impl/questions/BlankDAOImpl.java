package com.six.ems.dao.impl.questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.questions.BlankDAO;
import com.six.ems.entity.tables.Blank;

import com.six.ems.utils.CollectionUtils;

public class BlankDAOImpl implements BlankDAO {

    @Override
    public List<Blank> getAllBlank() {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("available",new SelectItem(1));
        return getBlanksByCondition(condition);
    }

    @Override
    public List<Blank> getBlanksByCondition(Map<String, SelectItem> condition) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Blank.class,condition);
    }

    @Override
    public Integer deleteBlank(Integer blankId) {
        Blank blank = new Blank();
        blank.setBlankId(blankId);
        int row = AdvanceUtil.delete(blank);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer addBlank(Blank blank) {
        int row = AdvanceUtil.insert(blank);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer updateBlank(Blank blank) {
        int row = AdvanceUtil.update(blank);
        return row > 0 ? row : 0;
    }

    @Override
    public Blank getBlankByBlankId(Integer blankId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("blank_id", new SelectItem(blankId));
        condition.put("available",new SelectItem(1));
        List<Blank> query = AdvanceUtil.select(Blank.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public int total() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        List<Blank> blanks = AdvanceUtil.select(Blank.class, condition);
        return blanks!=null? blanks.size():0;
    }

    @Override
    public List<Blank> blankQuery(int page, int rows) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return blankQuery(condition,page,rows);
    }

    @Override
    public List<Blank> blankQuery(Map<String, SelectItem> condition, int page, int rows) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Blank.class,condition, page, rows);
    }

    @Override
    public List<Blank> getBlankByCourseId(Integer courseId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("course_id", new SelectItem(courseId));
        condition.put("available", new SelectItem(1));
        List<Blank> query = AdvanceUtil.select(Blank.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

}
