package com.six.ems.web.service.interfaces.questions;

import java.util.List;

import com.six.ems.entity.tables.Blank;

public interface BlankService {
	
	/**
	 * 根据id查询所有填空题
	 */
	Blank getBlankByBlankId(Integer blankId);
	/**
	 * 查询所有填空题
	 */
	List<Blank> getAllBlank();
	/**
	 * 根据课程id查询填空题
	 */
	List<Blank> getBlankByCourseId(Integer courseId);
	
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
	/*
	 * 分页
	 */
	int total();
	public String blankQuery(int page, int rows);
}
