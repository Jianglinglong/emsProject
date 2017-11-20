package com.six.ems.entity.tables;


import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

/**
 * 用户角色关系实体类
 *
 * @author Direct
 */
@SQLTableName(table = "t_user_role")
public class UserRole {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "user_role_id")
    private Integer userRoleId;
    @SQLColnum(colName = "user_id")
    private Integer userId;
    @SQLColnum(colName = "role_id")
    private Integer roleId;
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public UserRole() {
        super();
    }
    
    
	public UserRole(Integer userId) {
		this.userId = userId;
	}


	public UserRole(Integer userID, Integer roleID) {
        this.userId = userID;
        this.roleId = roleID;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
