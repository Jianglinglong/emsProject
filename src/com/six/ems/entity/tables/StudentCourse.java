package com.six.ems.entity.tables;


import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_student_course")
public class StudentCourse {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "stu_course_id")
    private Integer stuCourseId;
    @SQLColnum(colName = "stu_id")
    private Integer stuId;
    @SQLColnum(colName = "course_id")
    private Integer CourseId;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public StudentCourse() {

    }

    public StudentCourse(Integer stuCourseId) {
        this.stuCourseId = stuCourseId;
    }

    public StudentCourse(Integer stuId, Integer courseId) {
        this.stuId = stuId;
        CourseId = courseId;
    }

    public StudentCourse(Integer stuCourseId, Integer stuId, Integer courseId) {
        this.stuCourseId = stuCourseId;
        this.stuId = stuId;
        CourseId = courseId;
    }

    public Integer getStuCourseId() {
        return stuCourseId;
    }

    public void setStuCourseId(Integer stuCourseId) {
        this.stuCourseId = stuCourseId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getCourseId() {
        return CourseId;
    }

    public void setCourseId(Integer courseId) {
        CourseId = courseId;
    }

}
