package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_class_course")
public class ClassCourse {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "class_course_id")
    private Integer classCourseId;
    @SQLColnum(colName = "class_id")
    private Integer classId;
    @SQLColnum(colName = "course_id")
    private Integer courseId;

    public ClassCourse() {
    }

    public ClassCourse(Integer classCourseId) {
        this.classCourseId = classCourseId;
    }

    public ClassCourse(Integer classCourseId, Integer classId, Integer courseId) {
        this.classCourseId = classCourseId;
        this.classId = classId;
        this.courseId = courseId;
    }

    public Integer getClassCourseId() {
        return classCourseId;
    }

    public void setClassCourseId(Integer classCourseId) {
        this.classCourseId = classCourseId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
