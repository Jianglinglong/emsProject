package com.six.ems.dao.impl.questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.questions.SubjectiveDAO;
import com.six.ems.entity.tables.Subjective;
import com.six.ems.utils.CollectionUtils;

public class SubjectiveDAOImpl implements SubjectiveDAO {

    @Override
    public List<Subjective> getAllSubjective() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available", new SelectItem(1));
        List<Subjective> query = AdvanceUtil.select(Subjective.class, condition);

        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public Integer deleteSubjective(Integer subjectiveId) {
        Subjective subjective = new Subjective();
        subjective.setSubId(subjectiveId);
        int row = AdvanceUtil.delete(subjective);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer addSubjective(Subjective subjective) {
        int row = AdvanceUtil.insert(subjective);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer updateSubjective(Subjective subjective) {
        int row = AdvanceUtil.update(subjective);
        return row > 0 ? row : 0;
    }

    @Override
    public Subjective getSubjectiveBySubId(Integer subId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("sub_id", new SelectItem(subId));
        condition.put("available", new SelectItem(1));
        List<Subjective> query = AdvanceUtil.select(Subjective.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public int total() {

        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available", new SelectItem(1));
        List<Subjective> subjectives = AdvanceUtil.select(Subjective.class, condition);
        return subjectives != null ? subjectives.size() : 0;
    }

    @Override
    public List<Subjective> sbujectiveQuery(int page, int rows) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available", new SelectItem(1));
        return AdvanceUtil.select(Subjective.class, condition, page, rows);
    }

    @Override
    public List<Subjective> getSubjectiveByCourseId(Integer courseId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("course_id", new SelectItem(courseId));
        condition.put("available", new SelectItem(1));
        List<Subjective> query = AdvanceUtil.select(Subjective.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

}
