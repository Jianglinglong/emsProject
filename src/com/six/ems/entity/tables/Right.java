package com.six.ems.entity.tables;

import com.jll.jdbc.content.SQLColnum;
import com.jll.jdbc.content.SQLPrimaryKey;
import com.jll.jdbc.content.SQLTableName;

/**
 * 权限管理实体类
 *
 * @author qingge
 * @data 2017年10月9日
 */
@SQLTableName(table = "t_right")
public class Right {
    @SQLPrimaryKey(auto = true)
    @SQLColnum(colName = "right_id")
    private Integer rightId;// 权限id
    @SQLColnum(colName = "parent_id")
    private Integer parentId;// 父权限id
    @SQLColnum(colName = "right_name")
    private String rightName;// 权限名称
    @SQLColnum(colName = "right_url")
    private String rightUrl;// 资源路径
    @SQLColnum(colName = "available")
    private String available = "1";

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public Right() {

    }

    public Right(Integer rightId) {
        this.rightId = rightId;
    }

    public Right(Integer rightId, Integer parentId, String rightName, String rightUrl) {
        this.rightId = rightId;
        this.parentId = parentId;
        this.rightName = rightName;
        this.rightUrl = rightUrl;
    }

    public Integer getRightId() {
        return rightId;
    }

    public void setRightId(Integer rightId) {
        this.rightId = rightId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightUrl() {
        return rightUrl;
    }

    public void setRightUrl(String rightUrl) {
        this.rightUrl = rightUrl;
    }

}
