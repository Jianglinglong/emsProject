<%--
  Created by IntelliJ IDEA.
  User: Direct
  Date: 2017/10/12
  Time: 17:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>role</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="role-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddRoleDialog()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditRoleDialog()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeRoleItem()"
                   plain="true">删除</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="roles-datagrid" toolbar="#role-toolbar"></table>
    </div>
    <div id="roles-right-window">
        <ul id="roles-right-tree">
        </ul>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加和修改页面div-start -->
<div id="roles-dialog" style="width:400px; padding:10px;">
    <form id="roles-form" method="post">
        <div style="display: none">角色ID: <input class="easyui-textbox" readonly="readonly" name="roleId"></div>
        <p>角色名称：<input class="easyui-validatebox" data-options="required:true" name="roleName"></p>
    </form>
</div>
<script type="text/javascript">
    function openAddRoleDialog() {
        $("#roles-dialog").dialog({
            closed: false,
            modal: true,
            title: "添加角色",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateRole('role.do?method=addRole');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#roles-dialog').dialog('close');
                }
            }]
        });
    }

    function updateRole(url) {
        $('#roles-form').form('submit', {
            url: url,
            onSubmit: function () {
                var isValid = $(this).form('validate');
                return isValid;	// 返回false终止表单提交
            },
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('提示', '添加成功', 'info');
                    $("#roles-dialog").dialog('close');
                    $("#roles-datagrid").datagrid('reload');
                }
            }
        })
    }

    function openEditRoleDialog() {
        var rows = $('#roles-datagrid').datagrid('getSelections');
        if (rows.length != 1) {
            $.messager.alert('提示', '请选中一行', 'info');
            return;
        }
        $('#roles-form').form('load', rows[0]);
        $("#roles-dialog").dialog({
            closed: false,
            modal: true,
            title: "修改角色",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateRole('role.do?method=updateRole')
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#roles-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 删除记录
     */
    function removeRoleItem() {
        var items = $('#roles-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.roleId);
                    });
                    var url = "role.do?method=deleteRole";
                    $.get(url, {roelID: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#roles-datagrid").datagrid("reload");// 重新加载数据库
                            $('#roles-dialog').dialog('close');
                        } else if (data == "NK") {
                            $.messager.alert('信息提示', '删除部分！', 'info');
                            $("#roles-datagrid").datagrid("reload");// 重新加载数据库
                            $('#roles-dialog').dialog('close');
                        }
                        else {
                            $.messager.alert('信息提示', '删除失败！', 'info');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('信息提示', '请选择要的删除数据！', 'info');
        }
    }

    /**
     * Name 载入数据
     */
    $(function () {
        $('#roles-datagrid').datagrid({
            url: 'role.do?method=getAllRoles',
            singleSelect: false,
            rownumbers: true,
            pageSize: 20,
            pagination: true,
            multiSort: true,
            fitColumns: true,
            fit: true,
            columns: [[
                {field: '', checkbox: true},
                {field: 'roleId', title: '角色ID', width: 100, hidden: true},
                {field: 'roleName', title: '角色名称', width: 180, sortable: true},
                {
                    field: 's', title: '操作', width: 80,
                    formatter: function (value, row, index) {
                        return "<a href='javascript:showRightsByID(" + row.roleId + ");'>管理权限</a> ";
                    }
                }

            ]]
        });
    });

    function showRightsByID(roleID) {
        $('#roles-right-window').dialog({
            width: 600,
            height: 400,
            closed: false,
            modal: true,
            title: "权限详细",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    var newRights = getChecked();
                    $.get('admin.do?method=updateRightsByRoleID',
                        {roleId: roleID, rights: newRights},
                        function (data) {
                            if (data == 'OK') {
                                $.messager.alert('提示', '权限修改成功', 'tipinf');
                                $('#roles-right-window').dialog('close');
                            }
                        });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#roles-right-window').dialog('close');
                }
            }]
        });
        $('#roles-right-tree').tree({
            url: 'role.do?method=getRightsByRoleID&roleId=' + roleID,
            animate: true,
            checkbox: true,
            lines: true
        });
    }

    function getChecked() {
        var nodes = $('#roles-right-tree').tree('getChecked');
        var rightIds = [];
        for (var i = 0; i < nodes.length; i++) {
            rightIds.push(nodes[i].id);
        }
        var nodes = $('#roles-right-tree').tree('getChecked', 'indeterminate');
        for (var i = 0; i < nodes.length; i++) {
            rightIds.push(nodes[i].id);
        }
        return rightIds.toString();
    }
</script>
</body>
</body>
</html>
