<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>学生管理</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="stu-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddStudent()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditStudent()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeStudent()"
                   plain="true">删除</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="leadStudent()" plain="true">导入</a>
                <form style="display: inline-block;" name="searchstudentform" method="post" action=""
                      id="searchstudentform">
                    姓名:<input id="stusearchname" class="easyui-textbox" name="stuName"/>
                    班级:<input id="stusearchclass" class="easyui-textbox" name="stuClass"/>
                    <a id="stusearchbtn" class="easyui-linkbutton">搜索</a>
                    <a id="sturesetbtn" class="easyui-linkbutton">重置</a>
                    <!-- <input id="stusearchbtn" type="button" value="搜索"/>
                    <input id="sturesetbtn" type="button" value= "重置"/> -->
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="stu-datagrit" toolbar="#stu-toolbar"></table>
    </div>
</div>

<!-- 设置权限start -->
<div id="grant-stu-roles"></div>
<!-- 设置权限end -->

<!-- 学生导入start -->
<div id="stu-import">
    <form id="f-imp" method="post" style="margin:30px 40px;" enctype="multipart/form-data">
        <input id="stu-txt" name="excel" style="width: 300px">
    </form>
</div>
<!-- 学生导入结束 end -->

<!-- Begin of easyui-dialog -->
<!-- 添加和修改页面div-start -->
<div id="stu_add" class="easyui-dialog"
     data-options="closed:true,iconCls:'icon-save'"
     style="width: 400px; padding: 10px;">
    <form id="stu-form" method="post">
        <table>
            <tr>
                <td><input type="hidden" name="stuId"/></td>
            </tr>
            <tr>
                <td width="60" align="right">姓名:</td>
                <td><input type="text" name="stuRealName" class="easyui-textbox"/></td>
            </tr>
            <tr>
                <td align="right">账号:</td>
                <td><input type="text" name="stuAccount" class="easyui-textbox"/></td>
            </tr>
            <%--<tr>--%>
                <%--<td align="right">密码:</td>--%>
                <%--<td><input type="text" name="stuPassword" class="easyui-textbox"/></td>--%>
            <%--</tr>--%>
                <input type="hidden" name="stuPassword" id="stuPassword"/>
        </table>
    </form>
</div>
<!-- 添加 和修改页面div-end -->
<!-- 进度条 -->
<div class="easyui-progressbar" data-options="value:100" style="width:400px;"></div>

<!-- End of easyui-dialog -->
<script type="text/javascript">

    /* $('#p').progressbar({
        value: 100
    });  */

    /**
     * Name 删除记录
     */
    function removeStudent() {
        var items = $('#stu-datagrit').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.stuId);
                    });
                    var url = "studentServlet.do?method=deleteStudent";
                    $.get(url, {stuId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#stu-datagrit").datagrid("reload");// 重新加载数据库
                            $('#stu_add').dialog('close');
                        } else if (data == "NK") {
                            $.messager.alert('信息提示', '删除部分！', 'info');
                            $("#stu-datagrit").datagrid("reload");// 重新加载数据库
                            $('#stu_add').dialog('close');
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

    /**
     * Name 打开添加窗口
     */
    function openAddStudent() {
        $('#stu-form').form('clear');
        $('#stu_add').dialog({
            closed: false,
            modal: true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    addAndUpdate('studentServlet.do?method=addStudent');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#stu_add').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEditStudent() {
        var rows = $('#stu-datagrit').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert("提示信息", "只能选择一行", "info");
        } else {
            var row = $('#stu-datagrit').datagrid('getSelected');
            if (row != null) {
                $('#stu_add').dialog(
                    {
                        closed: false,
                        modal: true,
                        title: "修改信息",
                        buttons: [{
                            text: '确定',
                            iconCls: 'icon-ok',
                            handler: function () {
                                addAndUpdate('studentServlet.do?method=updateStudent');
                            }
                        },
                            {
                                text: '取消',
                                iconCls: 'icon-cancel',
                                handler: function () {
                                    $('#stu_add').dialog('close');
                                }
                            }]
                    });
                $('#stu-form').form('load', row);
            } else {
                $.messager.alert("信息提示", "请选择修改的数据", "info");
            }
        }
    }

    /*
     * 添加和修改方法
     */
    var addAndUpdate = function (url) {
        $('#stuPassword').val('123456');
        $('#stu-form').form('submit', {
            url: url,
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('信息提示', '提交成功！', 'info');
                    $("#stu-datagrit").datagrid("reload");// 重新加载数据库
                    $('#stu_add').dialog('close');
                } else {
                    $.messager.alert('信息提示', '提交失败！', 'info');
                }
            }
        });
    };

    /**
     * Name 载入数据
     */
    $(function () {
        $('#stu-datagrit').datagrid(
            {
                url: 'studentServlet.do?method=queryStudent',
                rownumbers: true,
                singleSelect: false,
                pageSize: 20,
                pagination: true,
                multiSort: true,
                fitColumns: true,
                fit: true,
                queryParams: formStudentJson(),// 在请求远程数据的时候发送额外的参数。
                columns: [[
                    {field: '', checkbox: true},
                    {field: 'stuId', title: '编号', width: 100, sortable: true},
                    {field: 'stuRealName', title: '姓名', width: 180, sortable: true},
                    {field: 'stuAccount', title: '账号', width: 100},
                    //{field : 'stuPassword',title : '密码',width : 100},
                    {
                        field: "operation", title: '操作', width: 100,
                        formatter: function (value, row, index) {
                            return "<a onclick='grantStuRole(" + row.stuId + ");'>设置角色</a>";
                        }
                    }
                ]]
            });
    });


    /* 搜索方法*/
    $("#stusearchbtn").click(function () {
        //点击搜索
        $('#stu-datagrit').datagrid({
            queryParams: formStudentJson()
        });
    });

    /*重置方法*/
    $("#sturesetbtn").click(function () {
        $("#searchstudentform").form('clear');
        // 重新加载数据
        $('#stu-datagrit').datagrid({
            queryParams: formStudentJson()
        });
    });

    //将表单数据转为json
    function formStudentJson() {
        var stuName = $("#stusearchname").val();
        var stuClass = $("#stusearchclass").val();
        // 返回json
        return {"stuName": stuName, "stuClass": stuClass};
    }


    function grantStuRole(stuId) {
        $("#grant-stu-roles").dialog({
            width: 400,
            height: 200,
            top: 100,
            closed: false,
            model: true,
            title: "修改角色",
            href: 'role.do?method=initGrantStuRole&stuId=' + stuId,
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#grant-role-form').form('submit', {
                        url: 'role.do?method=updateRoleForUser',
                        success: function (data) {
                            $('#grant-role-form').remove();
                            $.messager.alert('提示', '设置成功', 'icon-tip')
                            $("#grant-stu-roles").dialog('close')
                        }
                    })
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $("#grant-stu-roles").dialog('close')
                }
            }]
        });

    }

    // 设置文本框
    $(function () {
        $("#stu-txt").filebox({
            buttonText: '请选择导入的excel文件',
            buttonAlign: "right",
            height: 40
        });
    });

    // 导入学生
    function leadStudent() {
        $("#f-imp").form('clear');// 清空导入框表单
        $("#stu-import").dialog({
            width: 400,
            height: 200,
            title: "导入学生信息",
            closed: false,
            model: true,
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#f-imp").form('submit', {
                        url: 'studentServlet.do?method=importExcel',
                        onSubmit: function () {
                            var isValid = $(this).form('validate');
                            if (isValid) {
                                //$.messager.progress(); // 如显示进度条
                                $.messager.progress({
                                    title: '提示',
                                    msg: '正在导入...',
                                    text: '导入中',
                                });
                            } else {
                                $.messager.progress('close'); // 如果表单是无效的则隐藏进度条
                            }
                            return isValid; // 返回false终止表单提交
                        },
                        success: function (data) {
                            if (data == "OK") {
                                $("#stu-datagrit").datagrid("reload");// 重新加载数据库
                                $.messager.alert('信息提示', '提交成功！');
                                $.messager.progress('close');// 关闭进度条
                                $('#stu-import').dialog('close');

                            } else {
                                $.messager.progress('close');
                                $.messager.alert('信息提示', '导入失败！');
                                $('#stu-import').dialog('close');
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#stu-import').dialog('close');
                }
            }]
        });
    }


</script>
</body>
</html>

