package com.six.ems.web.service.interfaces.course;

import java.util.List;

import com.six.ems.entity.tables.Course;

/**
 * 科目管理service接口
 * @author qingge
 * @data 2017年10月12日
 */
public interface CourseService {
	/**
	 * 查询科目方法
	 */
	public String courseQuery(int page, int rows);
	
	/**
	 * 查询所有科目方法
	 */
	public List<Course> courseQuery();
	
	/**
	 * 添加科目方法
	 */
	public int courseAdd(Course course);
	
	/**
	 * 修改科目方法
	 */
	public int courseUpdate(Course course);
	
	/**
	 * 删出科目方法
	 */
	public int courseDelete(Course course);

	Course getCourseByID(Integer courseID);
}
