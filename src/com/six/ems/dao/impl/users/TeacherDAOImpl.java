package com.six.ems.dao.impl.users;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.users.TeacherDAO;
import com.six.ems.entity.tables.Answer;
import com.six.ems.entity.tables.Teacher;
import com.six.ems.entity.tables.TeacherCourse;
import com.six.ems.utils.CollectionUtils;

/**
 * 实现TeacherDAO接口
 *
 * @author qingge
 * @data 2017年10月12日
 */
public class TeacherDAOImpl implements TeacherDAO {

    /**
     * 实现查询方法
     */
    public List<Teacher> teacherQuery(int page, int rows) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        List<Teacher> list = AdvanceUtil.select(Teacher.class,condition, page, rows);
        return list;
    }

    /**
     * 查询所有教师信息
     */
    public List<Teacher> getAllTeacher() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Teacher.class,condition);
    }

    /**
     * 根据id查询教师信息
     *
     * @param teaId
     * @return
     */
    public Teacher getTeacherById(Integer teaId) {
        // 构建条件
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("tea_id", new SelectItem(teaId));
        condition.put("available", new SelectItem(1));
        List<Teacher> list = AdvanceUtil.select(Teacher.class, condition);
        return CollectionUtils.isNotBlank(list) ? list.get(0) : null;
    }

    /**
     * 实现添加方法
     */
    public int teacherAdd(Teacher teacher) {
        return AdvanceUtil.insert(teacher);
    }

    /**
     * 实心修改方法
     */
    public int teacherUpdate(Teacher teacher) {
        return AdvanceUtil.update(teacher);
    }

    /**
     * 实现删除方法
     */
    public int teacherDelete(Teacher teacher) {
        return AdvanceUtil.delete(teacher);
    }


    public List<Answer> getAnswerByCondition(Map<String, SelectItem> condition) {
        condition.put("available",new SelectItem(1));
        return AdvanceUtil.select(Answer.class, condition);
    }

    @Override
    public List<TeacherCourse> geTeacherCoursesByTeaId(Integer teaId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        condition.put("tea_id", new SelectItem(teaId));
        List<TeacherCourse> query = AdvanceUtil.select(TeacherCourse.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public List<TeacherCourse> getTeacherCoursesByPage(Integer pageSize, Integer page) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        List<TeacherCourse> query = AdvanceUtil.select(TeacherCourse.class,condition, page, pageSize);

        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public List<TeacherCourse> getTeacherCourses() {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("available",new SelectItem(1));
        List<TeacherCourse> query = AdvanceUtil.select(TeacherCourse.class,condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }

    @Override
    public Integer addTeacherCourse(TeacherCourse teacherCourse) {
        int row = AdvanceUtil.insert(teacherCourse);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer updateTeacherCourse(TeacherCourse teacherCourse) {
        int row = AdvanceUtil.update(teacherCourse);
        return row > 0 ? row : 0;
    }

    @Override
    public Integer deleteTeacherCourse(TeacherCourse teacherCourse) {
        int row = AdvanceUtil.delete(teacherCourse);
        return row > 0 ? row : 0;
    }


}
