package com.six.ems.entity.utils;

import com.six.ems.entity.tables.StudentClass;

public class StuClassView extends StudentClass {
    private String stuName;
    private String className;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
