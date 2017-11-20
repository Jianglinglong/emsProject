package com.six.ems.web.service.impl.questions;

import java.util.List;

import com.six.ems.dao.impl.questions.JudgeDAOImpl;
import com.six.ems.dao.interfaces.questions.JudgeDAO;
import com.six.ems.entity.tables.Judge;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.interfaces.questions.JudgeService;

public class JudgeServiceImpl implements JudgeService {
	private JudgeDAO judgeDAO = new JudgeDAOImpl();
	@Override
	public List<Judge> getAllJudge() {
		
		return judgeDAO.getAllJudge();
	}

	@Override
	public Integer deleteJudge(Integer judgeId) {

		return judgeDAO.deleteJudge(judgeId);
	}

	@Override
	public Integer addJudge(Judge judge) {

		return judgeDAO.addJudge(judge);
	}

	@Override
	public Integer updateJudge(Judge judge) {

		return judgeDAO.updateJudge(judge);
	}

	@Override
	public Judge getJudgeByJudgeId(Integer judgeId) {
		
		return judgeDAO.getJudgeByJudgeId(judgeId);
	}

	@Override
	public int total() {
		
		return judgeDAO.total();
	}

	@Override
	public String judgeQuery(int page, int rows) {
		// 调用方法查询信息的总记录数
		List<Judge> list= judgeDAO.judgekQuery(page, rows);
		int total=judgeDAO.getAllJudge().size();
		String json=CollectionUtils.creatDataGritJson(list,total);
		//返回json字符串
		return json;
	}

	public List<Judge> getJudgeByCourseId(Integer courseId) {

		return judgeDAO.getJudgeByCourseId(courseId);
	}

}
