package com.six.ems.dao.interfaces.questions;

import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Choice;

public interface ChoiceDAO {
	
	/*
	 *分页 
	 */
	
	int total();
	public List<Choice> choiceQuery(int page, int rows);
	public List<Choice> choiceQuery(Map<String,SelectItem>condition, int page, int rows);
	/**
	 * 查询所有选择题区分单选多选
	 */
	List<Choice> getAllChoice(Integer choiceType, Integer courseId);
	
	/**
	 * 查询选择题根据id
	 */
	Choice getChoiceByChoiceId(Integer choiceId);
	
	/**
	 * 查询所有选择题
	 */
	List<Choice> getAllChoice();
	
	/**
	 * 删除选择题
	 */
	Integer deleteChoice(Integer choiceId);
	
	/**
	 * 添加选择题
	 */
	Integer addChoice(Choice choice);
	
	/**
	 * 修改选择题
	 */
	Integer updateChoice(Choice choice);
	
	/**
	 * 查询选择题业务方法
	 */
	Choice getChoiceByID(Integer choiceID);
}
