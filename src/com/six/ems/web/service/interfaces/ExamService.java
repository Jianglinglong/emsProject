package com.six.ems.web.service.interfaces;

import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Classes;
import com.six.ems.entity.tables.Exam;
import com.six.ems.entity.tables.Location;
import com.six.ems.entity.tables.Paper;
import com.six.ems.entity.tables.Teacher;

public interface ExamService {
/**
 * 开始考试的业务方法
 */
	public String startExam(String stuId, String courseId);
	
	
	
	/**
	 * 获取题号对应的正确答案
	 */
	public List<String> getAnswersByIds(String ids, String tableName, String id);
	/**
	 * 获取安排表的方法
	 * 
	 */
	public List<String> getExamById(String stuId);

/**
 * 修改exam的方法
 * @param exam
 * @return
 */
	public int examUpdate(Exam exam);
	
	/**
	 * 添加exam的方法
	 * @param exam
	 * @return
	 */
	public int examAdd(Exam exam);
	
	/**
	 * 根据试卷中的条件查询老师
	 */
	List<Teacher> getTeacherByCondition(Map<String, SelectItem> condition);
	/**
	 * 查询班级名称
	 */
	List<Classes> getClassByCondition();
	/**
	 * 根据班级id获取班级名称
	 */
	Classes getClassByClassId(Integer classId);
	/**
	 * 查询考试地点
	 */
	List<Location> getLocationByCondition();
	
	/**
	 * 根据条件查询教室
	 */
	List<Location> getLocationByLocationCondition(Location location);
}
