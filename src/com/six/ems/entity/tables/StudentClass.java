package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_stu_class")
public class StudentClass {
    @SQLColnum(colName = "stu_class_id")
    private Integer stuClassId;
    @SQLColnum(colName = "stu_id")
    private Integer stuId;
    @SQLColnum(colName = "class_id")
    private Integer classId;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public StudentClass() {
    }

    public StudentClass(Integer stuClassId) {
        this.stuClassId = stuClassId;
    }

    public StudentClass(Integer stuId, Integer classId) {
        this.stuId = stuId;
        this.classId = classId;
    }

    public Integer getStuClassId() {
        return stuClassId;
    }

    public void setStuClassId(Integer stuClassId) {
        this.stuClassId = stuClassId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
