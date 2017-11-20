package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;
import com.six.ems.utils.Md5Utils;

/**
 * 学生信息实体类
 *
 * @author Direct
 */
@SQLTableName(table = "t_student")
public class Student {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "stu_id")
    private Integer stuId;
    @SQLColnum(colName = "stu_real_name")
    private String stuRealName;
    @SQLColnum(colName = "stu_account")
    private String stuAccount;
    @SQLColnum(colName = "stu_password")
    private String stuPassword;
//    @SQLColnum(colName = "stu_class")
//    private String stuClass;
    @SQLColnum(colName = "available")
    private String available = "1";

	public Student() {}

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Student(Integer stuId) {
        this.stuId = stuId;
    }

    public Student(Integer stuId, String available) {
		this.stuId = stuId;
		this.available = available;
	}

	public Student(String account, String password) {
        this.stuAccount = account;
        this.stuPassword = password;
    }

    public Student(String stuRealName, String stuAccount, String stuPassword) {
        super();
        this.stuRealName = stuRealName;
        this.stuAccount = stuAccount;
        this.stuPassword = stuPassword;
        //this.stuClass = stuClass;
    }

    public Student(String stuAccount) {
        this.stuAccount = stuAccount;
    }

    public Integer getStuId() {
        return stuId;
    }

    public void setStuId(Integer stuId) {
        this.stuId = stuId;
    }

    public String getStuRealName() {
        return stuRealName;
    }

    public void setStuRealName(String stuRealName) {
        this.stuRealName = stuRealName;
    }

    public String getStuAccount() {
        return stuAccount;
    }

    public void setStuAccount(String stuAccount) {
        this.stuAccount = stuAccount;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = Md5Utils.GetMD5Code(stuPassword);
    }

//    public String getStuClass() {
//        return stuClass;
//    }
//
//    public void setStuClass(String stuClass) {
//        this.stuClass = stuClass;
//    }
  
    
}
