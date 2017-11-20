package com.six.ems.web.service.interfaces.questions;

import java.util.List;

import com.six.ems.entity.tables.Choice;

public interface ChoiceService {
	/**
	 * 获取所有选择题数据业务方法区分单选和多选
	 */
	List<Choice> getAllChoice(Integer choiceType, Integer courseId);
	/**
	 * 根据id获取选择题
	 */
	Choice getChoiceByChoiceId(Integer choiceId);
	
	/**
	 * 获取所有选择题数据业务方法
	 */
	List<Choice> getAllChoice();
	
	/**
	 * 删除选择题业务方法
	 */
	Integer deleteChoice(Integer choiceId);
	
	/**
	 * 添加选择题业务方法
	 */
	Integer addChoice(Choice choice);
	
	/**
	 * 修改选择题业务方法
	 */
	Integer updateChoice(Choice choice);



    /**
	 * 查询选择题业务方法
	 */
	int total();
	public String choiceQuery(int page, int rows);
}
