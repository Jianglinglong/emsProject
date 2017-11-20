package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_class")
public class Classes {
    @SQLColnum(colName = "class_id")
    private Integer classId;
    @SQLColnum(colName = "class_name")
    private String className;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Classes() {
    }

    public Classes(Integer classId) {
        this.classId = classId;
    }

    public Classes(Integer classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public Classes(String className) {
        this.className = className;
    }

    public Integer getClassId() {
        return classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
