package com.six.ems.dao.impl.papers;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.papers.PaperDAO;
import com.six.ems.entity.tables.Paper;
import com.six.ems.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PaperDAOImpl implements PaperDAO {

    @Override
    public Paper getPaperByID(Integer paperID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("paper_id", new SelectItem(paperID));
        condition.put("available", new SelectItem(1));
        List<Paper> papers = AdvanceUtil.select(Paper.class, condition);
        return CollectionUtils.isNotBlank(papers) ? papers.get(0) : null;
    }

    @Override
    public List<Paper> getPapersByID(Map<String, SelectItem> condition) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Paper.class, condition);
    }

    @Override
    public List<Paper> getAllPapers() {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("available",new SelectItem(1));
        List<Paper> query = AdvanceUtil.select(Paper.class,condition);

        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public int deletePaper(Integer paperId) {
        Paper paper = new Paper();
        paper.setPaperID(paperId);
        int row = AdvanceUtil.delete(paper);
        return row > 0 ? row : 0;
    }

    @Override
    public int addPaper(Paper paper) {
        int row = AdvanceUtil.insert(paper);
        return row > 0 ? row : 0;
    }

    @Override
    public int updatePaper(Paper paper) {
        int row = AdvanceUtil.update(paper);
        return row > 0 ? row : 0;
    }

    @Override
    public List<Paper> getPagePapers(Integer pageSize, Integer page) {
//        List<Paper> query = AdvanceUtil.select(Paper.class, page, pageSize);
//        return CollectionUtils.isNotBlank(query) ? query : null;
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return getPagePapers(condition,pageSize,page);
    }

    @Override
    public List<Paper> getPagePapers(Map<String, SelectItem> condition, Integer pageSize, Integer page) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Paper.class,condition,page,pageSize);
    }

    @Override
    public List<Paper> getPaperByRuleId(Integer ruleId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("rule_id", new SelectItem(ruleId));
        condition.put("available",new SelectItem(1));
        List<Paper> query = AdvanceUtil.select(Paper.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public List<Paper> getPaperByCourseId(Integer courseId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("course_id", new SelectItem(courseId));
        condition.put("available",new SelectItem(1));
        List<Paper> query = AdvanceUtil.select(Paper.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }
}
