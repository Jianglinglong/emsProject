package com.six.ems.dao.interfaces.questions;

import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Blank;

public interface BlankDAO {
	/*
	 * 分页
	 */
	int total();
	public List<Blank> blankQuery(int page, int rows);
	public List<Blank> blankQuery(Map<String,SelectItem> condition, int page, int rows);

	/**
	 * 查询所有填空题
	 */
	List<Blank> getAllBlank();
	List<Blank> getBlanksByCondition(Map<String, SelectItem> condition);

	
	
	
	/**
	 * 根据id查询所有填空题
	 */
	Blank getBlankByBlankId(Integer blankId);
	
	/**
	 * 删除填空题
	 */
	Integer deleteBlank(Integer blankId);
	
	/**
	 * 添加填空题
	 */
	Integer addBlank(Blank blank);
	
	/**
	 * 修改填空题
	 */
	Integer updateBlank(Blank blank);
	
	List<Blank> getBlankByCourseId(Integer courseId);
}
