package com.six.ems.entity.tables;


import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;
import com.six.ems.utils.Md5Utils;

/**
 * 教师信息实体类
 *
 * @author Direct
 */
@SQLTableName(table = "t_teacher")
public class Teacher {
	@SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "tea_id")
    private Integer teaId;
    @SQLColnum(colName = "tea_real_name")
    private String teaRealName;
    @SQLColnum(colName = "tea_account")
    private String teaAccount;
    @SQLColnum(colName = "tea_password")
    private String teaPassword;
    @SQLColnum(colName = "tea_remark")
    private String teaRemark;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Teacher() {
        super();
    }

    public Teacher(String account, String password) {
        this.teaAccount = account;
        this.teaPassword = password;
    }

    public Teacher(Integer teaId) {
        this.teaId = teaId;
    }

    public Integer getTeaId() {
        return teaId;
    }

    public void setTeaId(Integer teaId) {
        this.teaId = teaId;
    }

    public String getTeaRealName() {
        return teaRealName;
    }

    public void setTeaRealName(String teaRealName) {
        this.teaRealName = teaRealName;
    }

    public String getTeaAccount() {
        return teaAccount;
    }

    public void setTeaAccount(String teaAccount) {
        this.teaAccount = teaAccount;
    }

    public String getTeaPassword() {
        return teaPassword;
    }

    public void setTeaPassword(String teaPassword) {

        this.teaPassword = Md5Utils.GetMD5Code(teaPassword);
    }

    public String getTeaRemark() {
        return teaRemark;
    }

    public void setTeaRemark(String teaRemark) {
        this.teaRemark = teaRemark;
    }
}
