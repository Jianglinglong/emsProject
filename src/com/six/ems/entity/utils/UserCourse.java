package com.six.ems.entity.utils;

public class UserCourse {
    private Integer teaId;
    private String teaName;

    private Integer teaCourseId;
    private Integer stuCourseId;

    private Integer stuId;
    private String stuName;

    private Integer courseId;
    private String courseName;
    public UserCourse() {
    }

    public Integer getTeaCourseId() {
        return teaCourseId;
    }

    public void setTeaCourseId(Integer teaCourseId) {
        this.teaCourseId = teaCourseId;
    }

    public Integer getStuCourseId() {
        return stuCourseId;
    }

    public void setStuCourseId(Integer stuCourseId) {
        this.stuCourseId = stuCourseId;
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

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }
}
