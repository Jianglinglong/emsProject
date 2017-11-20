package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

/**
 * 考试科目管理实体类
 *
 * @author qingge
 * @data 2017年10月9日
 */
@SQLTableName(table = "t_course")
public class Course {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "course_id")
    private Integer courseId;// 科目id
    @SQLColnum(colName = "course_name")
    private String courseName;// 科目名称
    @SQLColnum(colName = "enable")
    private String enable;// 是否启用
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Course() {

    }

    public Course(Integer courseId) {
        this.courseId = courseId;
    }

    public Course(Integer courseId, String courseName, String enable) {
        super();
        this.courseId = courseId;
        this.courseName = courseName;
        this.enable = enable;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

}
