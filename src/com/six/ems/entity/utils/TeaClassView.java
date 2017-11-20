package com.six.ems.entity.utils;

import com.six.ems.entity.tables.TeacherClass;

public class TeaClassView extends TeacherClass {
    private String teaName;
    private String className;

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
