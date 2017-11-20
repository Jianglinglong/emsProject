package com.six.ems.web.service.interfaces.score;


import java.util.List;

import com.six.ems.entity.tables.Score;

/**
 * 定义ScoreService接口
 * @author qingge
 * @data 2017年10月12日
 */
public interface ScoreService {

	/**
	 * 根据学生id查询成绩
	 * @param stuId
	 * @param page
	 * @param rows
	 * @return 返回集合
	 */
	public List<Score> scoreQuery(Integer stuId, int page, int rows);
	/**
	 * 成绩查询分页
	 * @param page
	 * @param rows
	 * @return 
	 */
	public String scoreQuery(int page, int rows);
	
	/**
	 * 根据成绩编号查询成绩
	 * @param scoreId
	 * @return
	 */
	public Score getScoreByScoreId(Integer scoreId);
	
	/**
	 * 添加成绩方法
	 */
	public int scoreAdd(Score score);
	
	/**
	 * 修改成绩方法
	 */
	public int scoreUpdate(Score score);
	
	/**
	 * 删出成绩方法
	 */
	public int scoreDelete(Score score);
	
	/**
	 * 通过学生id和成绩id获取成绩
	 */
	public Score getScoreByStuId(Integer stuId, Integer paperId);

	/**
	 * 通过学生id获取成绩
	 */
	public List<Score> getScoreByStuId(Integer stuId);
	
    /**
     * 查询所有信息
     * @return
     */
	public List<Score> getScores();
	
//	public String getScoreIdByStuId(Integer userId);
	
	/**
	 * 查询成绩方法
	 */
//	public String scoreQuery(Class<Score> score,int page,int rows);
	
}
