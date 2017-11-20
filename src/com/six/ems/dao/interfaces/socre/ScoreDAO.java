package com.six.ems.dao.interfaces.socre;


import java.util.List;

import com.six.ems.entity.tables.Score;
import com.six.ems.entity.tables.TeacherCourse;

/**
 * 成绩接口
 * @author Direct
 * @data 2017年10月12日
 */
public interface ScoreDAO {
	
	/**
	 * 根据学生id查询成绩  分页
	 */
	public List<Score> scoreQuery(Integer stuId, int page, int rows);
	
	/**
	 * 查询所有信息  分页
	 * @param page
	 * @param rows
	 * @return
	 */
	public List<Score> scoreQuery(int page, int rows);
	
	/**
	 * 根据成绩编号查询成绩
	 * @param scoreId
	 * @return
	 */
	public Score getScoreByScoreId(Integer scoreId);
	
	/**
	 * 查询所有成绩
	 * @return
	 */
	public List<Score> getScores();
	
	/**
	 * 通过学生id获取成绩
	 */
	public List<Score> getScoreByStuId(Integer stuId);
	
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
	 * 通过学生id和试卷id获取学生成绩
	 */
	public Score getScoreByStuId(Integer stuId, Integer paperId);
	
	/*// 老师界面不显示成绩用的方法
	public String getScoreIdByStuId1(Integer userId);*/
	
	/**
	 * 获取所有老师的课程关系表带分页
	 */
//	List<TeacherCourse> getTeacherCoursesByPage(Integer pageSize, Integer page);
}
