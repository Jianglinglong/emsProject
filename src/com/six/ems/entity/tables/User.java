package com.six.ems.entity.tables;


import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

/**
 * 用户信息实体类
 *com.jll.jdbc.content.SQLTableName
 * @author Direct
 */
@SQLTableName(table = "t_user")
public class User {
	@SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "user_id")
    private Integer userId;
    @SQLColnum(colName = "tea_id")
    private Integer teaId;
    @SQLColnum(colName = "stu_id")
    private Integer stuId;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public User() {
        super();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }
}
