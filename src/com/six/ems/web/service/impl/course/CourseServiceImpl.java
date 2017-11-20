package com.six.ems.web.service.impl.course;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.six.ems.dao.impl.course.CourseDAOImpl;
import com.six.ems.dao.interfaces.course.CourseDAO;
import com.six.ems.entity.tables.Course;
import com.six.ems.web.service.interfaces.course.CourseService;

/**
 * 科目管理service接口
 * @author qingge
 * @data 2017年10月12日
 */
public class CourseServiceImpl implements CourseService {

	// 创建CourseDAO对象
	private CourseDAO courseDao = new CourseDAOImpl();
	/**
	 * 查询科目方法
	 */
	public String courseQuery(int page, int rows) {
		// 调用方法查询所有信息  （分页）
		List<Course> list = courseDao.courseQuery(page, rows);
		// 调用方法查询信息的总记录数
		int total = courseDao.getAllCourse().size();
		// 将查询到的信息存入集合
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", list);
		map.put("total", total);
		// 将信息集合转为json字符串
		String jsonString = JSON.toJSONString(map,true);
		// 返回json字符串
		return jsonString;
	}
	
	/**
	 * 查询所有科目(不分页)
	 */
	public List<Course> courseQuery() {
		return courseDao.courseQuery();
	}

	/**
	 * 添加科目方法
	 */
	public int courseAdd(Course course) {
		return courseDao.courseAdd(course);
	}

	/**
	 * 修改科目方法
	 */
	public int courseUpdate(Course course) {
		return courseDao.courseUpdate(course);
	}

	/**
	 * 删除科目方法
	 */
	public int courseDelete(Course course) {
		return courseDao.courseDelete(course);
	}

	@Override
	public Course getCourseByID(Integer courseID) {
		return courseDao.courseQueryByID(courseID);
	}


}
