package com.six.ems.dao.interfaces.papers;


import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Paper;

import java.util.List;
import java.util.Map;

public interface PaperDAO {
	/**
	 * 根据试卷id获取试卷信息
	 * @param paperID
	 * @return
	 */
	Paper getPaperByID(Integer paperID);
	
	/**
	 * 获取所有试卷信息
	 */
	List<Paper> getAllPapers();
	/**
	 * 查询特定的试卷信息
	 * @param condition
	 * @return
	 */
	List<Paper> getPapersByID(Map<String, SelectItem> condition);
	/**
	 * 删除试卷信息
	 */
	int deletePaper(Integer paperId);
	/**
	 * 添加试卷信息
	 */
	int addPaper(Paper paper);
	/**
	 * 修改试卷信息
	 */
	int updatePaper(Paper paper);
	/**
	 * 分页查询试卷信息
	 */
	List<Paper> getPagePapers(Integer pageSize, Integer page);
	List<Paper> getPagePapers( Map<String,SelectItem> condition,Integer pageSize, Integer page);

	/**,
	 * 根据规则id查询试卷
	 * @param ruleId
	 * @return
	 */
	List<Paper> getPaperByRuleId(Integer ruleId);
	
	/**
	 * 根据课程id获取试卷
	 * @param courseId
	 * @return
	 */
	List<Paper> getPaperByCourseId(Integer courseId);
	
}
