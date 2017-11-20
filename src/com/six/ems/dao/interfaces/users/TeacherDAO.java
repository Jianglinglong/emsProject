package com.six.ems.dao.interfaces.users;

import java.util.List;

import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.TeacherCourse;


/**
 * 实现TeacherDAO接口
 * @author qingge
 * @data 2017年10月12日
 */
public interface TeacherDAO {
    /*boolean check(Teacher teacher);
    Teacher getTeacherByID(Integer teacherID);*/
	
	/**
	 * 查询老师方法
	 */
	public List<Teacher> teacherQuery(int page, int rows);
	
	/**
	 * 获取所有教师信息
	 * @return
	 */
	public List<Teacher> getAllTeacher();
	
	/**
	 * 根据id查询教师信息
	 * @param teaId
	 * @return
	 */
	public Teacher getTeacherById(Integer teaId);
	
	/**
	 * 添加老师方法
	 */
	public int teacherAdd(Teacher teacher);
	
	/**
	 * 修改老师方法
	 */
	public int teacherUpdate(Teacher teacher);
	
	/**
	 * 删出老师方法
	 */
	public int teacherDelete(Teacher teacher);

	/**
	 * 根据条件获取答题卡
	 * @param condition
	 * @return
	 */

	/**
	 * 根据老师id查询老师课程关系表
	 */
	List<TeacherCourse> geTeacherCoursesByTeaId(Integer teaId);
	
	/**
	 * 获取所有老师的课程关系表带分页
	 */
	List<TeacherCourse> getTeacherCoursesByPage(Integer pageSize, Integer page);
	
	/**
	 * 获取所有老师课程关系
	 */
	List<TeacherCourse> getTeacherCourses();
	
	/**
	 * 添加老师课程关系
	 */
	Integer addTeacherCourse(TeacherCourse teacherCourse);
	
	/**
	 * 修改老师课程关系
	 */
	Integer updateTeacherCourse(TeacherCourse teacherCourse);
	
	/**
	 * 删除老师课程关系
	 */
	Integer deleteTeacherCourse(TeacherCourse teacherCourse);
}
