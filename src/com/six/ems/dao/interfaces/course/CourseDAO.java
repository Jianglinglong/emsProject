package com.six.ems.dao.interfaces.course;

import java.util.List;
import java.util.Map;

import com.jll.jdbc.base.SelectItem;
import com.six.ems.entity.tables.Course;

/**
 * 科目管理接口
 *
 * @author qingge
 * @data 2017年10月12日
 */
public interface CourseDAO {
    /**
     * 查询所有科目方法(分页)
     */
    public List<Course> courseQuery(int page, int rows);

    public List<Course> courseQuery(Map<String, SelectItem> condition, int page, int rows);

    /**
     * 查询所有科目
     *
     * @return
     */
    public List<Course> getAllCourse();

    /**
     * 查询所有科目方法
     */
    public List<Course> courseQuery();

    /**
     * 添加科目方法
     */
    public int courseAdd(Course course);

    /**
     * 修改科目方法
     */
    public int courseUpdate(Course course);

    /**
     * 删出科目方法
     */
    public int courseDelete(Course course);

    Course courseQueryByID(Integer courseID);

    /*
     * 根据id查询科目
     */
    List<Course> getCourseById(Integer courseId);

}
