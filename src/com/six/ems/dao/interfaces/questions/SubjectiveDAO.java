package com.six.ems.dao.interfaces.questions;

import java.util.List;

import com.six.ems.entity.tables.Subjective;

public interface SubjectiveDAO {
	
	
	/*
	 * 分页
	 */
	int total();
	public List<Subjective> sbujectiveQuery(int page, int rows);
	/**
	 * 查询所有主观题
	 */
	List<Subjective> getAllSubjective();
	/**
	 * 根据id查询主观题
	 */
	Subjective getSubjectiveBySubId(Integer subId);
	
	/**
	 * 删除主观题
	 */
	Integer deleteSubjective(Integer subjectiveId);
	
	/**
	 * 添加主观题
	 */
	Integer addSubjective(Subjective subjective);
	
	/**
	 * 修改主观题
	 */
	Integer updateSubjective(Subjective subjective);
	
	List<Subjective> getSubjectiveByCourseId(Integer courseId);
}
