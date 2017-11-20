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
        <div id="course-class-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddCourseClassDialog()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit"
                   onclick="openEditCourseClassesDialog()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeCourseClassesItem()"
                   plain="true">删除</a>
                <form id="courseClassSearchForm" method="post" style="display: inline-block">
                    <%--//科目：<input class="easyui-textbox" name="courseName"/>--%>
                    班级：<input class="easyui-textbox" name="courseClassName"/>
                    <a id="courseClassSearchbtn" class="easyui-linkbutton">搜索</a>
                    <a id="courseClassResetbtn" class="easyui-linkbutton">重置</a>
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="course-class-datagrid" toolbar="#course-class-toolbar"></table>
    </div>
    <div id="course-class-dialog" style="width: 300px;height: 193px;padding: 20px" class="easyui-dialog" data-options="closed:true">
        <form method="post" id="course-class-form">
            <table>
                <tr>
                    <td><input type="hidden" name="classCourseId"></td>
                </tr>
                <tr>
                    <th>科目</th>
                    <td><input type="text" name="courseId" id="courseIdCommobox" editable="false" panelMaxHeight="100"></td>
                </tr>
                <tr>
                    <th>班级</th>
                    <td><input type="text" name="classId" id="courseClassIdCommobox" editable="false" panelMaxHeight="100"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加和修改页面div-start -->

<script type="text/javascript">
    function openAddCourseClassDialog() {
        $('#course-class-form').form('clear');
        //$('#courseIdCommobox').combobox('readonly',false);
        $("#course-class-dialog").dialog({
            closed: false,
            modal: true,
            title: "关联科目",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateclasses('class.do?method=addCourseClass');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#course-class-dialog').dialog('close');
                }
            }]
        });
    }

    function updateclasses(url) {
        $('#course-class-form').form('submit', {
            url: url,
            onSubmit: function () {
                var isValid = $(this).form('validate');
                return isValid;	// 返回false终止表单提交
            },
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('提示', '更新成功', 'info');
                    $("#course-class-dialog").dialog('close');
                    $("#course-class-datagrid").datagrid('reload');
                }else {
                    $.messager.alert('提示', '更新失败', 'info');
                    $("#course-class-dialog").dialog('close');
                }
            }
        })
    }

    function openEditCourseClassesDialog() {
        var rows = $('#course-class-datagrid').datagrid('getSelections');
        if (rows.length != 1) {
            $.messager.alert('提示', '请选中一行', 'info');
            return;
        }
        $('#course-class-form').form('load', rows[0]);

        $("#course-class-dialog").dialog({
            closed: false,
            modal: true,
            title: "修改科目",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateclasses('class.do?method=updateCourseClass')
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#course-class-dialog').dialog('close');
                }
            }]
        });
       // $('#courseIdCommobox').combobox('readonly');
    }

    /**
     * Name 删除记录
     */
    function removeCourseClassesItem() {
        var items = $('#course-class-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.classCourseId);
                    });
                    var url = "class.do?method=deleteClassCourse";
                    $.get(url, {classCourseId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#course-class-datagrid").datagrid("reload");// 重新加载数据库
                            $('#course-class-dialog').dialog('close');
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

    function loadCommoboxForcourseClass() {
        //初始化班级下拉列表
        $('#courseClassIdCommobox').combobox({
            valueField: 'id',
            textField: 'name',
            editable:false,
            url: 'class.do?method=getClassJson',
        });
        //初始化教师下拉列表
        $('#courseIdCommobox').combobox({
            valueField: 'id',
            textField: 'name',
            editable:false,
            url: 'teacherServlet.do?method=getCourseJson',
        });
    }
    /**
     * Name 载入数据
     */
    $(function () {
        loadCommoboxForcourseClass();
        $('#course-class-datagrid').datagrid({
            url: 'class.do?method=getCouseClass',
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
                {field: 'classCourseId',  width: 100, hidden: true},
                {field: 'classId',  hidden: true, width: 100},
                {field: 'className', title: '班级', width: 100},
                {field: 'courseId',  width: 180, hidden: true},
                {field: 'courseName', title: '科目', width: 180},
            ]]
        });
    });
    /* 搜索方法*/
    $("#courseClassSearchbtn").click(function () {
        //点击搜索
        $('#course-class-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    /*重置方法*/
    $("#courseClassResetbtn").click(function () {
        $("#courseClassSearchForm").form('clear');
        // 重新加载数据
        $('#course-class-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    //将表单数据转为json
    function formClassJson() {
        var data = $('#courseClassSearchForm').serializeArray();
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
