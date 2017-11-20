package com.six.ems.web.service.interfaces.paper;

import java.util.List;

import com.six.ems.entity.tables.Paper;

public interface PaperService {
	/**
	 * 根据试卷id获取试卷信息
	 */
	Paper getPaperById(Integer paperId);
	/**
	 * 获取所有试卷信息
	 */
	List<Paper> getAllPapers();
	/**
	 * 分页获取试卷信息
	 */
	List<Paper> getPagePapers(Integer pageSize, Integer page);
	
	/**
	 * 根据id删除试卷信息
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
	 * 根据规则id查找试卷
	 */
	List<Paper> getPaperByRuleId(Integer ruleId);
	
	/**
	 * 根据课程id查找试卷
	 */
	List<Paper> getPaperByCourseId(Integer courseId);
}
