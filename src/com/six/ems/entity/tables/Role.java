package com.six.ems.entity.tables;


import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

/**
 * 角色管理实体类
 *
 * @author Direct
 */
@SQLTableName(table = "t_role")
public class Role {
	@SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "role_id")
    private Integer roleId;// 角色id
    @SQLColnum(colName = "role_name")
    private String roleName;// 角色姓名
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Role() {
        super();
    }

    public Role(Integer roleId) {
        this.roleId = roleId;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
