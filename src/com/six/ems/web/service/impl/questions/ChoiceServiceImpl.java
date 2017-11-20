package com.six.ems.web.service.impl.questions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.six.ems.dao.impl.questions.ChoiceDAOImpl;
import com.six.ems.dao.interfaces.questions.ChoiceDAO;
import com.six.ems.entity.tables.Choice;
import com.six.ems.utils.CollectionUtils;
import com.six.ems.web.service.interfaces.questions.ChoiceService;

public class ChoiceServiceImpl implements ChoiceService {
private ChoiceDAO choiceDAO = new ChoiceDAOImpl();
	@Override
	public List<Choice> getAllChoice(Integer choiceType,Integer courseId) {
		
		return choiceDAO.getAllChoice(choiceType,courseId);
	}

	@Override
	public Integer deleteChoice(Integer choiceId) {
		
		return choiceDAO.deleteChoice(choiceId);
	}

	@Override
	public Integer addChoice(Choice choice) {
		
		return choiceDAO.addChoice(choice);
	}

	@Override
	public Integer updateChoice(Choice choice) {

		return choiceDAO.updateChoice(choice);
	}


	@Override
	public List<Choice> getAllChoice() {
		
		return choiceDAO.getAllChoice();
	}

	@Override
	public Choice getChoiceByChoiceId(Integer choiceId) {

		return choiceDAO.getChoiceByChoiceId(choiceId);
	}


	@Override
	public String choiceQuery(int page, int rows) {
		// 调用方法查询信息的总记录数
		List<Choice> list = choiceDAO.choiceQuery(page, rows);
		List<Choice> allChoice = choiceDAO.getAllChoice();
		int total = 0;
		if(CollectionUtils.isNotBlank(allChoice)) {
			total = allChoice.size();
		}
		// 创建Map对象
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", list);
		map.put("total", total);
		//转为json字符串
		String jsonString = JSON.toJSONString(map);
		// 返回json字符串
		return jsonString;   	
	}

	@Override
	public int total() {
		return choiceDAO.total() ;
	}

}
