package com.six.ems.dao.interfaces.users;

import java.util.List;

import com.six.ems.entity.tables.Student;
import com.six.ems.entity.tables.StudentCourse;

/**
 * 实现studentDAO接口
 * @author qingge
 * @data 2017年10月12日
 */
public interface StudentDAO {
    /*Double getScore(Student student);
    boolean beginTest(Paper paper);*/
    
	/**
	 * 查询学生方法  分页
	 */
	public List<Student> studentQuery(int page, int rows);
	
	/**
	 * 查询所有学生信息
	 * @return
	 */
	public List<Student> getAllStudent();
	
	/**
	 * 通过学生id查询学生信息
	 * @param stuId传入学生id
	 * @return 返回学生对象
	 */
	public Student getStudentById(Integer stuId);
	
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
	
	/**
	 *  根据学生id获取课程信息
	 */
	List<StudentCourse> getStudentCourseByStuId(Integer studentId);
}
