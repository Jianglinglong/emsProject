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
        <div id="tea-class-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddTeaClassesDialog()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit"
                   onclick="openEditTeaClassesDialog()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeTeaClassesItem()"
                   plain="true">删除</a>
                <form id="teaClassSearchForm" method="post" style="display: inline-block">
                    教师：<input class="easyui-textbox" name="teaName"/>
                    班级：<input class="easyui-textbox" name="teaClassName"/>
                    <a id="teaClassSearchbtn" class="easyui-linkbutton">搜索</a>
                    <a id="teaClassResetbtn" class="easyui-linkbutton">重置</a>
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="tea-class-datagrid" toolbar="#tea-class-toolbar"></table>
    </div>
    <div id="tea-class-dialog" style="width: 300px;height: 193px;padding: 20px"
         class="easyui-dialog" data-options="closed:true"
    >
        <form method="post" id="tea-class-form">
            <table>
                <tr>
                    <td><input type="hidden" name="userClassId"></td>
                </tr>
                <tr>
                    <th>教师</th>
                    <td><input type="text" id="teaIdCommobox" name="teaId" editable="false" panelMaxHeight="100"></td>
                </tr>
                <tr>
                    <th>班级</th>
                    <td><input type="text" id="teaClassIdCommobox" name="classId"editable="false" panelMaxHeight="100"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加和修改页面div-start -->

<script type="text/javascript">


    function loadCommoboxForTeaClass() {
        //初始化班级下拉列表
        $('#teaClassIdCommobox').combobox({
            valueField: 'id',
            textField: 'name',
            url: 'class.do?method=getClassJson',
        });
        //初始化教师下拉列表
        $('#teaIdCommobox').combobox({
            valueField: 'id',
            textField: 'name',
            url: 'teacherServlet.do?method=getTeacherCJson',
        });
    }

    function openAddTeaClassesDialog() {
        $('#stuIdCommobox').combobox('readonly',false);
        $('#tea-class-form').form('clear');
        $("#tea-class-dialog").dialog({
            closed: false,
            modal: true,
            title: "分配班级",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateclasses('class.do?method=addTeaClass');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#tea-class-dialog').dialog('close');
                }
            }]
        });
//        loadCommoboxForTeaClass();
    }

    function updateclasses(url) {
        $('#tea-class-form').form('submit', {
            url: url,
            onSubmit: function () {
                var isValid = $(this).form('validate');
                return isValid;	// 返回false终止表单提交
            },
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('提示', '更新成功', 'info');
                    $("#tea-class-dialog").dialog('close');
                    $("#tea-class-datagrid").datagrid('reload');
                }else {
                    $.messager.alert('提示', '更新失败', 'info');
                    $("#tea-class-dialog").dialog('close');
                }
            }
        })
    }

    function openEditTeaClassesDialog() {
        var rows = $('#tea-class-datagrid').datagrid('getSelections');
        if (rows.length != 1) {
            $.messager.alert('提示', '请选中一行', 'info');
            return;
        }
        $('#tea-class-form').form('clear');
        $('#tea-class-form').form('load', rows[0]);
        $("#tea-class-dialog").dialog({
            closed: false,
            modal: true,
            title: "修改班级",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateclasses('class.do?method=updateTeaClass')
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#tea-class-dialog').dialog('close');
                }
            }]
        });
        $('#teaIdCommobox').combobox('readonly',true);
//        loadCommoboxForTeaClass();
    }

    /**
     * Name 删除记录
     */
    function removeTeaClassesItem() {
        var items = $('#tea-class-datagrid').datagrid('getSelections');
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
                            $("#tea-class-datagrid").datagrid("reload");// 重新加载数据库
                            $('#tea-class-dialog').dialog('close');
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
        //初始化教师下拉列表
        //初始化班级下拉列表
        loadCommoboxForTeaClass();

        $('#tea-class-datagrid').datagrid({
            url: 'class.do?method=getTeaClassInfo',
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
                {field: 'teaId', title: '教师', width: 180, hidden: true},
                {field: 'teaName', title: '教师', width: 180},
            ]]
        });
    });
    /* 搜索方法*/
    $("#teaClassSearchbtn").click(function () {
        //点击搜索
        $('#tea-class-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    /*重置方法*/
    $("#teaClassResetbtn").click(function () {
        $("#teaClassSearchForm").form('clear');
        // 重新加载数据
        $('#tea-class-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    //将表单数据转为json
    function formClassJson() {

        var data = $('#teaClassSearchForm').serializeArray();
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
