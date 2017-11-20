package com.six.ems.web.service.interfaces.users;


import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.StudentCourse;

import java.util.List;

public interface StudentService {
	
	/**
	 * 通过学生id查询学生信息
	 * @param stuId 传入学生id
	 * @return 返回学生对象
	 */
	public Student getStudentById(Integer stuId);
	

	/**
	 * 查询学生方法
	 */
	public String studentQuery(int page, int rows);
	
	/**
	 * 添加学生方法
	 */
	public int studentAdd(Student student);
	
	/**
	 * 修改学生方法
	 */
	public int studentUpdate(Student student);
	
	/**
	 * 删出学生方法
	 */
	public int studentDelete(Student student);

	List<Student> getStudentsForPage(Integer page, Integer pageSize);

	/**
     * 根据学生id查询课程信息
     */
	List<StudentCourse> getStudentCourseByStuId(Integer studentId); 

}
