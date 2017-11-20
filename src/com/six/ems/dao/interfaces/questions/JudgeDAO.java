package com.six.ems.dao.interfaces.questions;

import java.util.List;


import com.six.ems.entity.tables.Judge;

public interface JudgeDAO {
	
	/*
	 * 分页
	 */
	int total();
	public List<Judge> judgekQuery(int page, int rows);
	
	/**
	 * 查询所有判断题
	 */
	List<Judge> getAllJudge();
	/**
	 * 根据id获取判断题
	 */
	Judge getJudgeByJudgeId(Integer judgeId);
	
	/**
	 * 删除判断题
	 */
	Integer deleteJudge(Integer judgeId);
	
	/**
	 * 添加判断题
	 */
	Integer addJudge(Judge judge);
	
	/**
	 * 修改判断题
	 */
	Integer updateJudge(Judge judge);
	
	List<Judge> getJudgeByCourseId(Integer courseId);
}
