package com.six.ems.dao.impl.exam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.exams.ExamsDAO;
import com.six.ems.entity.tables.Classes;
import com.six.ems.entity.tables.Exam;
import com.six.ems.entity.tables.Location;
import com.six.ems.entity.tables.Paper;
import com.six.ems.entity.tables.PaperRule;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.TeacherCourse;
import com.six.ems.utils.CollectionUtils;

public class ExamsDAOImpl implements ExamsDAO {

    @Override
    public boolean addPaper(Paper paper) {
        return AdvanceUtil.insert(paper) > 0;
    }

    @Override
    public boolean addPaperRule(PaperRule paperRule) {
        return AdvanceUtil.insert(paperRule) > 0;
    }

    @Override
    public int addExam(Exam exam) {

        return AdvanceUtil.insert(exam);
    }

    @Override
    public Exam getExamByID(Integer examID) {
        Exam exam = new Exam(examID);
        return exam;
    }

    @Override
    public PaperRule getPaperRuleByID(Integer paperRuleID) {
        return null;
    }

    @Override
    public Paper getPaperByID(Integer paperID) {
        Paper paper = new Paper(paperID);
        return paper;

    }

    @Override
    public Paper getPaperByRule(PaperRule paperRule) {
        return null;
    }

    @Override
    public int examUpdate(Exam exam) {
        return AdvanceUtil.update(exam);
    }

    @Override
    public String examQuery(Class<Exam> exam, int page, int rows) {
        // 调用工具类查询
        List<Exam> list = AdvanceUtil.select(Exam.class, page, rows);
        int total = AdvanceUtil.select(Exam.class).size();
        // 创建Map对象
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("total", total);
        map.put("rows", list);
        // 转为json字符串
        String CourseJson = JSON.toJSONString(map);
        // 返回json字符串
        return CourseJson;
    }

    @Override
    public List<Teacher> getTeacherByCondition(Map<String, SelectItem> condition) {
        List<TeacherCourse> teacherCourses = AdvanceUtil.select(TeacherCourse.class, condition);
        List<Teacher> teaList = new ArrayList<Teacher>();
        for (TeacherCourse teacherCourse : teacherCourses) {
            Map<String, SelectItem> map = new HashMap<>();
            map.put("tea_id", new SelectItem(teacherCourse.getTeaId()));
            List<Teacher> list = AdvanceUtil.select(Teacher.class, map);
            if (CollectionUtils.isNotBlank(list)) {
                teaList.add(list.get(0));
            }
        }

        return CollectionUtils.isNotBlank(teaList) ? teaList : null;
    }
    @Override
    public List<Classes> getClassById() {
	 	List<Classes> select = AdvanceUtil.select(Classes.class);

        return CollectionUtils.isNotBlank(select) ? select : null;
    }

	@Override
	public List<Location> getLocation() {
		List<Location> select = AdvanceUtil.select(Location.class);
		return CollectionUtils.isNotBlank(select)?select:null;
	}

	@Override
	public Classes getClassByClassId(Integer classId) {
		 // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("class_id", new SelectItem(classId));
        condition.put("available", new SelectItem(1));
        List<Classes> list = AdvanceUtil.select(Classes.class, condition);
        return CollectionUtils.isNotBlank(list) ? list.get(0) : null;
	
	}

	@Override
	public List<Location> getLocationByLocationCondition(Location location) {
		List<Location> locationList = AdvanceUtil.select(location);
		return CollectionUtils.isNotBlank(locationList) ? locationList : null;
	}
}
