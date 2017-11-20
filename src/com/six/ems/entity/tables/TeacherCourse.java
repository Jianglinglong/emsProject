package com.six.ems.entity.tables;


import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_teacher_course")
public class TeacherCourse {
	@SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "tea_course_id")
    private Integer teaCourseId;
    @SQLColnum(colName = "tea_id")
    private Integer teaId;
    @SQLColnum(colName = "course_id")
    private Integer courseId;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public TeacherCourse() {

    }

    public TeacherCourse(Integer teaId, Integer courseId) {
        this.teaId = teaId;
        this.courseId = courseId;
    }

    public Integer getTeaCourseId() {
        return teaCourseId;
    }

    public void setTeaCourseId(Integer teaCourseId) {
        this.teaCourseId = teaCourseId;
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
