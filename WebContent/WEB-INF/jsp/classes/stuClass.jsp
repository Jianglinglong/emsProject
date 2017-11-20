<%--
  Created by IntelliJ IDEA.
  User: Direct
  Date: 2017/10/26
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>classes</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="stu-class-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddStuClassesDialog()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit"
                   onclick="openEditStuClassesDialog()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeStuClassesItem()"
                   plain="true">删除</a>
                <form id="stuClassSearchForm" method="post" style="display: inline-block">
                    学生：<input class="easyui-textbox" name="stuName"/>
                    班级：<input class="easyui-textbox" name="stuClassName"/>
                    <a id="stuClassSearchbtn" class="easyui-linkbutton">搜索</a>
                    <a id="stuClassResetbtn" class="easyui-linkbutton">重置</a>
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="stu-class-datagrid" toolbar="#stu-class-toolbar"></table>
    </div>
    <div id="stu-class-dialog" style="width: 300px;height: 193px;padding: 20px" class="easyui-dialog" data-options="closed:true">
        <form method="post" id="stu-class-form">
            <table>
                <tr>
                    <td><input type="hidden" name="userClassId"></td>
                </tr>
                <tr>
                    <th>学生</th>
                    <td><input type="text" name="stuId" id="stuIdCommobox" editable="false" panelMaxHeight="100"></td>
                </tr>
                <tr>
                    <th>班级</th>
                    <td><input type="text" name="classId" id="stuClassIdCommobox" editable="false" panelMaxHeight="100"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加和修改页面div-start -->

<script type="text/javascript">
    function openAddStuClassesDialog() {
//        $('#stuClassIdCommobox').combobox({readonly:false});
        $('#stu-class-form').form('clear');
        $('#stuIdCommobox').combobox('readonly',false);
        $("#stu-class-dialog").dialog({
            closed: false,
            modal: true,
            title: "分配班级",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateclasses('class.do?method=addStuClass');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#stu-class-dialog').dialog('close');
                }
            }]
        });
    }

    function updateclasses(url) {
        $('#stu-class-form').form('submit', {
            url: url,
            onSubmit: function () {
                var isValid = $(this).form('validate');
                return isValid;	// 返回false终止表单提交
            },
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('提示', '更新成功', 'info');
                    $("#stu-class-dialog").dialog('close');
                    $("#stu-class-datagrid").datagrid('reload');
                }else {
                    $.messager.alert('提示', '更新失败', 'info');
                    $("#stu-class-dialog").dialog('close');
                }
            }
        })
    }

    function openEditStuClassesDialog() {
        var rows = $('#stu-class-datagrid').datagrid('getSelections');
        if (rows.length != 1) {
            $.messager.alert('提示', '请选中一行', 'info');
            return;
        }
        $('#stu-class-form').form('load', rows[0]);

        $("#stu-class-dialog").dialog({
            closed: false,
            modal: true,
            title: "修改班级",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateclasses('class.do?method=updateStuClass')
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#stu-class-dialog').dialog('close');
                }
            }]
        });
        $('#stuIdCommobox').combobox('readonly');
    }

    /**
     * Name 删除记录
     */
    function removeStuClassesItem() {
        var items = $('#stu-class-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.userClassId);
                    });
                    var url = "class.do?method=deleteUserClass";
                    $.get(url, {userClassId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#stu-class-datagrid").datagrid("reload");// 重新加载数据库
                            $('#stu-class-dialog').dialog('close');
                        } else {
                            $.messager.alert('信息提示', '删除失败！', 'info');
                        }
                    });
                }
            });
        } else {
            $.messager.alert('信息提示', '请选择要的删除数据！', 'info');
        }
    }

    function loadCommoboxForStuClass() {
        //初始化班级下拉列表
        $('#stuClassIdCommobox').combobox({
            valueField: 'id',
            textField: 'name',
            editable:false,
            url: 'class.do?method=getClassJson',
        });
        //初始化教师下拉列表
        $('#stuIdCommobox').combobox({
            valueField: 'id',
            textField: 'name',
            editable:false,
            url: 'studentServlet.do?method=getStudentJson',
        });
    }
    /**
     * Name 载入数据
     */
    $(function () {
        loadCommoboxForStuClass();
        $('#stu-class-datagrid').datagrid({
            url: 'class.do?method=getStuClassInfo',
            singleSelect: false,
            rownumbers: true,
            pageSize: 20,
            pagination: true,
            multiSort: true,
            fitColumns: true,
            fit: true,
            queryParams: formClassJson(),// 在请求远程数据的时候发送额外的参数。
            columns: [[
                {field: '', checkbox: true},
                {field: 'userClassId', title: '角色ID', width: 100, hidden: true},
                {field: 'classId', title: '班级', hidden: true, width: 100},
                {field: 'className', title: '班级', width: 100},
                {field: 'stuId', title: '学生', width: 180, hidden: true},
                {field: 'stuName', title: '学生', width: 180},
            ]]
        });
    });
    /* 搜索方法*/
    $("#stuClassSearchbtn").click(function () {
        //点击搜索
        $('#stu-class-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    /*重置方法*/
    $("#stuClassResetbtn").click(function () {
        $("#stuClassSearchForm").form('clear');
        // 重新加载数据
        $('#stu-class-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    //将表单数据转为json
    function formClassJson() {
        var data = $('#stuClassSearchForm').serializeArray();
        var obj = {};
        $.each(data, function (i, v) {
            obj[v.name] = v.value;
        })
        return obj;
    }
</script>
</body>
</body>
</html>
