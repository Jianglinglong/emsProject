package com.six.ems.dao.impl.course;


import com.jll.jdbc.base.SelectItem;
import com.jll.jdbc.tools.AdvanceUtil;
import com.six.ems.dao.interfaces.course.CourseDAO;
import com.six.ems.entity.tables.Course;
import com.six.ems.utils.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 科目管理接口实现类
 *
 * @author qingge
 * @data 2017年10月12日
 */
public class CourseDAOImpl implements CourseDAO {

    /**
     * 实现查询方法
     */
    public List<Course> courseQuery(int page, int rows) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return courseQuery(condition,page,rows);
    }

    @Override
    public List<Course> courseQuery(Map<String, SelectItem> condition, int page, int rows) {
        condition.put("available", new SelectItem(1));
        return AdvanceUtil.select(Course.class,condition,page,rows);
    }

    /**
     * 查询所有科目信息
     */
    public List<Course> getAllCourse() {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("available", new SelectItem(1));
        return AdvanceUtil.select(Course.class,condition);
    }

    /**
     * 实现添加方法
     */
    public int courseAdd(Course course) {
        // 调用工具类存入数据
        return AdvanceUtil.insert(course);
    }

    /**
     * 实现修改方法
     */
    public int courseUpdate(Course course) {
        // 调用工具类的update方法更新数据
        return AdvanceUtil.update(course);
    }

    /**
     * 实现删除方法
     */
    public int courseDelete(Course course) {
        // 返回删除影响的行数
        return AdvanceUtil.delete(course);
    }

    @Override
    public Course courseQueryByID(Integer courseID) {
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        condition.put("course_id", new SelectItem(courseID));
        condition.put("available", new SelectItem(1));
        List<Course> courses = AdvanceUtil.select(Course.class, condition);
        return CollectionUtils.isNotBlank(courses) ? courses.get(0) : null;
    }

    /**
     * 查询所有
     */
    public List<Course> courseQuery() {
//        List<Course> courseList = AdvanceUtil.select(Course.class);
//        return courseList;
        Map<String, SelectItem> condition = CollectionUtils.getCondition();
        return getCourseByCondition(condition);
    }

    /**
     * 根据id查询科目
     */
    @Override
    public List<Course> getCourseById(Integer courseId) {
        Map<String, SelectItem> condition = new HashMap<>();
        condition.put("course_id", new SelectItem(courseId));
        condition.put("available",new SelectItem(1));
        List<Course> query = AdvanceUtil.select(Course.class, condition);
        return CollectionUtils.isNotBlank(query) ? query : null;
    }
    private List<Course> getCourseByCondition(Map<String,SelectItem> condition){
        condition.put("available",new SelectItem(1));
        return  AdvanceUtil.select(Course.class,condition);
    }

}
