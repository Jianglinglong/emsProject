package com.six.ems.dao.impl.questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.questions.JudgeDAO;
import com.six.ems.entity.tables.Judge;
import com.six.ems.utils.CollectionUtils;

public class JudgeDAOImpl implements JudgeDAO {

    @Override
    public List<Judge> getAllJudge() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available", new SelectItem(1));
        List<Judge> query = AdvanceUtil.select(Judge.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public Integer deleteJudge(Integer judgeId) {
        Judge judge = new Judge();
        judge.setJudgeId(judgeId);
        int row = AdvanceUtil.delete(judge);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer addJudge(Judge judge) {
        int row = AdvanceUtil.insert(judge);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer updateJudge(Judge judge) {
        int row = AdvanceUtil.update(judge);
        return row > 0 ? row : 0;
    }

    @Override
    public Judge getJudgeByJudgeId(Integer judgeId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("judge_id", new SelectItem(judgeId));
        condition.put("available", new SelectItem(1));
        List<Judge> query = AdvanceUtil.select(Judge.class, condition);
        return CollectionUtils.isNotBlank(query) ? query.get(0) : null;
    }

    @Override
    public int total() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available", new SelectItem(1));
        List<Judge> judges = AdvanceUtil.select(Judge.class, condition);
        return judges != null ? judges.size() : 0;
    }

    @Override
    public List<Judge> judgekQuery(int page, int rows) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available", new SelectItem(1));
        return AdvanceUtil.select(Judge.class, condition, page, rows);
    }

    @Override
    public List<Judge> getJudgeByCourseId(Integer courseId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("course_id", new SelectItem(courseId));
        List<Judge> query = AdvanceUtil.select(Judge.class, condition);

        return CollectionUtils.isNotBlank(query) ? query : null;
    }

}
