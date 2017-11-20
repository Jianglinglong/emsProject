package com.six.ems.dao.impl.answers;

import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.answers.AnswerDao;
import com.six.ems.entity.tables.Answer;
import com.six.ems.utils.CollectionUtils;

import java.util.List;
import java.util.Map;

public class AnswerDAOImpl implements AnswerDao {
    @Override
    public List<Answer> getAnswers() {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return getAnswerByCondition(condition);
    }

    @Override
    public List<Answer> getAnswerByCondition(Map<String, SelectItem> condition) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Answer.class,condition);
    }
}
