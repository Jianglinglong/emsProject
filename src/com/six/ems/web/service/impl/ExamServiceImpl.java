package com.six.ems.web.service.impl;

import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.dao.impl.exam.ExamsDAOImpl;
import com.six.ems.dao.interfaces.exams.ExamsDAO;
import com.six.ems.entity.tables.Classes;
import com.six.ems.entity.tables.Exam;
import com.six.ems.entity.tables.Location;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.web.service.interfaces.ExamService;

public class ExamServiceImpl implements ExamService {
	private ExamsDAO  examDAO = new ExamsDAOImpl();
	
	
	/**
	 * 实现查询方法
	 */
	public String examQuery(Class<Exam> teacher, int page, int rows) {
		return examDAO.examQuery(Exam.class, page, rows);
	}

	@Override
	public String startExam(String stuId, String courseId) {
		
		return null;
	}

	@Override
	public List<String> getAnswersByIds(String ids, String tableName, String id) {
		return null;
	}

	@Override
	public List<String> getExamById(String stuId) {
		
		return null;
	}

	@Override
	public int examUpdate(Exam exam) {
			return examDAO.examUpdate(exam);
	}

	@Override
	public int examAdd(Exam exam) {
	 	return examDAO.addExam(exam);
		
	}

	@Override
	public List<Teacher> getTeacherByCondition(Map<String, SelectItem> condition) {
		return examDAO.getTeacherByCondition(condition);
	}
	
	@Override
	public List<Classes> getClassByCondition() {
		
		return examDAO.getClassById();
	}

	@Override
	public List<Location> getLocationByCondition() {
		
		return examDAO.getLocation();
	}

	@Override
	public Classes getClassByClassId(Integer classId) {
		
		return  examDAO.getClassByClassId(classId);
	}

	@Override
	public List<Location> getLocationByLocationCondition(Location location) {
		
		return examDAO.getLocationByLocationCondition(location);
	}



}
