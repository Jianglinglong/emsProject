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
        <div id="classes-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddClassesDialog()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditClassesDialog()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeClassesItem()"
                   plain="true">删除</a>
                <form id="classSearchForm" method="post" style="display: inline-block" >
                    班级：<input class="easyui-textbox" id="className" name="className"/>
                    <a id="classSearchbtn" class="easyui-linkbutton">搜索</a>
                    <a id="classResetbtn" class="easyui-linkbutton">重置</a>
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="classes-datagrid" toolbar="#classes-toolbar"></table>
    </div>
    <div id="classes-dialog" style="width: 300px;height: 193px;padding: 30px" class="easyui-dialog" data-options="closed:true">
        <form id="classes-form" method="post">
            <table>
                <tr>
                    <td><input type="hidden" name="classId"> </td>
                </tr>
                <tr>
                    <th>班级</th>
                    <td><input type="text" name="className" class="easyui-textbox"></td>
                </tr>
            </table>
        </form>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加和修改页面div-start -->

<script type="text/javascript">
    function openAddClassesDialog() {
        $('#classes-form').form('clear');
        $("#classes-dialog").dialog({
            closed: false,
            modal: true,
            title: "添加课程",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateClasses('class.do?method=addClasses');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#classes-dialog').dialog('close');
                }
            }]
        });
    }

    function updateClasses(url) {
        $('#classes-form').form('submit', {
            url: url,
            onSubmit: function () {
                var isValid = $(this).form('validate');
                return isValid;	// 返回false终止表单提交
            },
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('提示', '更新成功', 'info');
                    $("#classes-dialog").dialog('close');
                    $("#classes-datagrid").datagrid('reload');
                }else {
                    $.messager.alert('提示', '更新失败', 'info');
                    $("#classes-dialog").dialog('close');
                }
            }
        })
    }

    function openEditClassesDialog() {
        $('#classes-form').form('clear');
        var rows = $('#classes-datagrid').datagrid('getSelections');
        if (rows.length != 1) {
            $.messager.alert('提示', '请选中一行', 'info');
            return;
        }
        $('#classes-form').form('load', rows[0]);
        $("#classes-dialog").dialog({
            closed: false,
            modal: true,
            title: "修改角色",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    updateClasses('class.do?method=updateClasses')
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#classes-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 删除记录
     */
    function removeClassesItem() {
        var items = $('#classes-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.classId);
                    });
                    var url = "class.do?method=deleteClasses";
                    $.get(url, {classId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#classes-datagrid").datagrid("reload");// 重新加载数据库
                            $('#classes-dialog').dialog('close');
                        }
                        else {
                            $.messager.alert('信息提示', '删除出错！', 'info');
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
        $('#classes-datagrid').datagrid({
            url: 'class.do?method=getClasses',
            singleSelect: false,
            rownumbers: true,
            pageSize: 20,
            pagination: true,
            multiSort: true,
            fitColumns: true,
            queryParams: formClassJson(),// 在请求远程数据的时候发送额外的参数。
            fit: true,
            columns: [[
                {field: '', checkbox: true},
                {field: 'classId', title: '班级编号', width: 100, hidden: true},
                {field: 'className', title: '班级名称', width: 180, sortable: true},
            ]]
        });
    });
    /* 搜索方法*/
    $("#classSearchbtn").click(function(){
        //点击搜索
        $('#classes-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    /*重置方法*/
    $("#classResetbtn").click(function(){
        $("#classSearchForm").form('clear');
        // 重新加载数据
        $('#classes-datagrid').datagrid({
            queryParams: formClassJson()
        });
    });

    //将表单数据转为json
    function formClassJson() {
        var className = $("#className").val();
        // 返回json
        return {"className":className};
    }
</script>
</body>
</body>
</html>
