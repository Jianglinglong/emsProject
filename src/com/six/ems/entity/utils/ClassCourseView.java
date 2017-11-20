package com.six.ems.entity.utils;

import com.six.ems.entity.tables.ClassCourse;

public class ClassCourseView extends ClassCourse {
    private String courseName;
    private String className;

    public ClassCourseView() {
    }

    public ClassCourseView(ClassCourse classCourse) {
        setClassCourseId(classCourse.getClassCourseId());
        setClassId(classCourse.getClassId());
        setCourseId(classCourse.getCourseId());
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
