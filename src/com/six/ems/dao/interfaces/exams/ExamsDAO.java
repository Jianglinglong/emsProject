package com.six.ems.dao.interfaces.exams;


import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Classes;
import com.six.ems.entity.tables.Exam;
import com.six.ems.entity.tables.Location;
import com.six.ems.entity.tables.Paper;
import com.six.ems.entity.tables.PaperRule;
import com.six.ems.entity.tables.Teacher;

public interface ExamsDAO {
	
    boolean addPaper(Paper paper);
   
    boolean addPaperRule(PaperRule paperRule);

    PaperRule getPaperRuleByID(Integer paperRuleID);
/**
 * 根据id获取试卷的方法
 * @param paperID
 * @return
 */
    Paper getPaperByID(Integer paperID);
    
    
    
    Paper getPaperByRule(PaperRule paperRule);

    /**
     * 根据id获得考试安排表
     * @param examID
     * @return
     */
    Exam getExamByID(Integer examID);
    /**
     * 添加考试安排
     * @param exam
     * @return
     */
    int addExam(Exam exam);
    /**
	 * 修改exam方法
	 * @param exam
	 * @return
	 */
	public int examUpdate(Exam exam);
	
	/**
	 * 查询exam方法
	 */
	public String examQuery(Class<Exam> exam, int page, int rows);
	
	/**
	 * 根据试卷中的条件查询老师
	 */
	List<Teacher> getTeacherByCondition(Map<String, SelectItem> condition);
    
	// 获取班级名称
	List<Classes> getClassById();
	// 获取地点名称
	List<Location> getLocation();
	// 根据班级id查班级名称
	public Classes getClassByClassId(Integer classId);
	
	/**
	 * 根据条件获取教室
	 */
	List<Location> getLocationByLocationCondition(Location location);
			
}
