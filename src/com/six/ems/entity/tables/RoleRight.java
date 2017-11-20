package com.six.ems.entity.tables;


import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

/**
 * 角色权限管理实体类
 *
 * @author qingge
 * @data 2017年10月9日
 */
@SQLTableName(table = "t_role_right")
public class RoleRight {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "role_right_id")
    private Integer roleRightId;// 角色权限管理id
    @SQLColnum(colName = "right_id")
    private Integer rightId;// 权限管理id
    @SQLColnum(colName = "role_id")
    private Integer roleId;// 角色管理id
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public RoleRight() {

    }

    public RoleRight(Integer roleRightId) {
        this.roleRightId = roleRightId;
    }

    public RoleRight(Integer roleRightId, Integer rightId, Integer roleId) {
        this.roleRightId = roleRightId;
        this.rightId = rightId;
        this.roleId = roleId;
    }

    public Integer getRoleRightId() {
        return roleRightId;
    }

    public void setRoleRightId(Integer roleRightId) {
        this.roleRightId = roleRightId;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

}
