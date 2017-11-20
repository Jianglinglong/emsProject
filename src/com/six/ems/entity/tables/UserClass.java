package com.six.ems.entity.tables;
import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

@SQLTableName(table = "t_user_class")
public class UserClass {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "user_class_id")
    private Integer userClassId;
    @SQLColnum(colName = "stu_id")
    private Integer stuId;
    @SQLColnum(colName = "tea_id")
    private Integer teaId;
    @SQLColnum(colName = "class_id")
    private Integer classId;
    @SQLColnum(colName = "available")
    private String available = "1";

    public UserClass() {
    }

    public UserClass(Integer userClassId) {
        this.userClassId = userClassId;
    }

    public UserClass(Integer stuId, Integer teaId, Integer classId) {
        this.stuId = stuId;
        this.teaId = teaId;
        this.classId = classId;
    }

    public Integer getUserClassId() {
        return userClassId;
    }

    public void setUserClassId(Integer userClassId) {
        this.userClassId = userClassId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
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

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }
}
