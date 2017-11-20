package com.six.ems.entity.utils;

import com.six.ems.entity.tables.UserClass;

public class UserClassView extends UserClass {
    private String stuName;
    private String teaName;
    private String className;



    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getTeaName() {
        return teaName;
    }

    public void setTeaName(String teaName) {
        this.teaName = teaName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
