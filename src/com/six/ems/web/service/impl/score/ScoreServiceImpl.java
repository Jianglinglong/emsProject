package com.six.ems.web.service.impl.score;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.six.ems.dao.impl.score.ScoreDAOImpl;
import com.six.ems.dao.interfaces.socre.ScoreDAO;
import com.six.ems.entity.tables.Score;
import com.six.ems.web.service.interfaces.score.ScoreService;

/**
 * 实现ScoreService接口
 * @author qingge
 * @data 2017年10月12日
 */
public class ScoreServiceImpl implements ScoreService {

	// 创建ScoreDAO实例
	private ScoreDAO scoreDao = new ScoreDAOImpl();
	
	
	/**
	 * 实现查询根据学生id
	 */
	public List<Score> scoreQuery(Integer stuId, int page, int rows) {
		return scoreDao.scoreQuery(stuId, page, rows);
	}
	
	/**
	 * 根据成绩编号查询成绩
	 */
	public Score getScoreByScoreId(Integer scoreId) {
		return scoreDao.getScoreByScoreId(scoreId);
	}

	/**
	 * 查询所有信息 分页  返回json字符串
	 */
	public String scoreQuery(int page, int rows) {
		// 调用方法查询所有
		List<Score> scoreQuery = scoreDao.scoreQuery(page, rows);
		// 调用方法查询信息总记录数
		int size = scoreDao.getScores().size();
		// 创建 存储集合对象
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", scoreQuery);
		map.put("total", size);
		// 将集合转为json字符串
		String jsonString = JSON.toJSONString(map);
		// 返回json字符串
		return jsonString;
	}
	
	/**
	 * 查询所有信息  返回一个信息集合
	 */
	public List<Score> getScores() {
		return scoreDao.getScores();
	}
	
	/**
	 * 实现添加方法
	 */
	public int scoreAdd(Score score) {
		return scoreDao.scoreAdd(score);
	}

	/**
	 * 实现修改方法
	 */
	public int scoreUpdate(Score score) {
		return scoreDao.scoreUpdate(score);
	}

	/**
	 * 实现删除方法
	 */
	public int scoreDelete(Score score) {
		return scoreDao.scoreDelete(score);
	}

	/**
	 * 通过学生id和试卷查询成绩
	 */
	public Score getScoreByStuId(Integer stuId, Integer paperId) {
		return scoreDao.getScoreByStuId(stuId, paperId);
	}

	/**
	 * 实现通过学生id查询学生成绩信息
	 */
	public List<Score> getScoreByStuId(Integer stuId) {
		List<Score> list = scoreDao.getScoreByStuId(stuId);
		return list;
	}

}
