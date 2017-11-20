package com.six.ems.dao.interfaces.answers;


import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Answer;

import java.util.List;
import java.util.Map;

public interface AnswerDao {
    List<Answer> getAnswers();
    List<Answer> getAnswerByCondition(Map<String, SelectItem> condition);
}
