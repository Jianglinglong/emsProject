package com.six.ems.web.service.impl.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.six.ems.dao.impl.users.StudentDAOImpl;
import com.six.ems.dao.interfaces.users.StudentDAO;
import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.StudentCourse;
import com.six.ems.web.service.interfaces.users.StudentService;

public class StudentServiceImpl implements StudentService {

	// 创建对象
	private StudentDAO studentDao = new StudentDAOImpl(); 
	
	/**
	 * 根据id查询学生信息
	 */
	public Student getStudentById(Integer stuId) {
		return studentDao.getStudentById(stuId);
	}
	
	/**
	 * 查询学生方法
	 */
	public String studentQuery(int page, int rows) {
		// 调用方法查询信息
		List<Student> list = studentDao.studentQuery(page, rows);
		// 调用方法查询信息的总记录数
		int total = studentDao.getAllStudent().size();
		// 创建Map对象
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("rows", list);
		map.put("total", total);
		//转为json字符串
		String jsonString = JSON.toJSONString(map);
		// 返回json字符串
		return jsonString;
	}

	/**
	 * 添加学生方法
	 */
	public int studentAdd(Student student) {
		return studentDao.studentAdd(student);
	}

	/**
	 * 修改学生方法
	 */
	public int studentUpdate(Student student) {
		return studentDao.studentUpdate(student);
	}

	/**
	 * 删除学生方法
	 */
	public int studentDelete(Student student) {
		return studentDao.studentDelete(student);
	}

	@Override
	public List<Student> getStudentsForPage(Integer page, Integer pageSize) {
		return studentDao.studentQuery(page,pageSize);
	}

	@Override
	public List<StudentCourse> getStudentCourseByStuId(Integer studentId) {
		
		return studentDao.getStudentCourseByStuId(studentId);
	}


}
