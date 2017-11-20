package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_tea_class")
public class TeacherClass {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "tea_class_id")
    private Integer teaClassId;
    @SQLColnum(colName = "tea_id")
    private Integer teaId;
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

    public Integer getTeaClassId() {
        return teaClassId;
    }

    public void setTeaClassId(Integer teaClassId) {
        this.teaClassId = teaClassId;
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
