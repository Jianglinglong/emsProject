<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>教师管理</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="tea-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddTeacher()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditTeacher()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeTeacher()"
                   plain="true">删除</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="leadTeacher()" plain="true">导入</a>
                <form style="display: inline-block;" name="searchteacherform" method="post" action=""
                      id="searchteacherform">
                    姓名:<input id="teasearchname" class="easyui-textbox" name="teaName"/>
                    <!-- <input id="teasearchclass" class="easyui-textbox"  hidden="hidden"  name="teaClass"/> -->
                    <!-- <input id="teasearchbtn" type="button" value="搜索"/>
                    <input id="tearesetbtn" type="button" value= "重置"/> -->
                    <a id="teasearchbtn" class="easyui-linkbutton">搜索</a>
                    <a id="tearesetbtn" class="easyui-linkbutton">重置</a>
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="tea-datagrit" toolbar="#tea-toolbar"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 学生导入start -->
<div id="tea-import">
    <form id="f-tea-imp" method="post" style="margin:30px 40px;" enctype="multipart/form-data">
        <input id="tea-txt" name="excel" style="width: 300px"
               accept=".csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet">
    </form>
</div>
<!-- 学生导入结束 end -->

<!-- 添加和修改页面div-start -->
<div id="tea_add" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'"
     style="width:400px; padding:10px;">
    <form id="tea-form" method="post">
        <table>
            <tr>
                <td><input type="hidden" name="teaId"/></td>
            </tr>
            <tr>
                <td width="60" align="right">姓名:</td>
                <td><input type="text" name="teaRealName" class="easyui-textbox"/></td>
            </tr>
            <tr>
                <td align="right">账号:</td>
                <td><input type="text" name="teaAccount" class="easyui-textbox"/></td>
            </tr>
            <%--<tr>--%>
            <%--<td align="right">密码:</td>--%>
            <%--<td><input type="hidden" name="teaPassword" value="123456"  /></td>--%>
            <%--</tr>--%>
            <input type="hidden" id="teaPassword" name="teaPassword" value="123456"/>
            <tr>
                <td valign="top" align="right">备注:</td>
                <td><input type="text" name="teaRemark" class="easyui-textbox"/></td>
            </tr>
        </table>
    </form>
</div>
<!-- 添加 和修改页面div-end -->
<div id="grant-tea-role"></div>
<!-- End of easyui-dialog -->
<!-- 引入下拉列表框值 -->
<script type="text/javascript">

    //设置输入框类型
    $(function () {
        $('#tea-txt').filebox({
            buttonText: '请选择导入的excel文件',
            buttonAlign: 'right',
            height: 40
        })
    });

    // 导入学生
    function leadTeacher() {
        $('#f-tea-imp').form('clear');
        $('#tea-import').dialog({
            title: '导入教师信息',
            width: 400,
            height: 200,
            closed: false,
            modal: true,
            buttons: [{
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#f-tea-imp").form('submit', {
                        url: 'teacherServlet.do?method=importExcel',
                        onSubmit: function () {
                            var isValid = $(this).form('validate');
                            if (isValid) {
                                //$.messager.progress();	// 显示进度条
                                $.messager.progress({
                                    title: '提示',
                                    msg: '正在导入...',
                                    text: '导入中',
                                });
                            } else {
                                $.messager.progress('close');// 如果表单是无效的则隐藏进度条
                            }
                            return isValid;	// 返回false终止表单提交
                        },
                        success: function (data) {
                            if (data == "OK") {
                                $("#tea-datagrit").datagrid("reload");// 重新加载数据库
                                $.messager.progress('close');
                                $.messager.alert('信息提示', '导入成功！');
                                $('#tea-import').dialog('close');
                            } else {
                                $.messager.progress('close');
                                $.messager.alert('信息提示', '导入失败！');
                                $('#tea-import').dialog('close');
                            }
                        }

                    });
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#tea-import').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 删除记录
     */
    function removeTeacher() {
        var items = $('#tea-datagrit').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.teaId);
                    });
                    var url = "teacherServlet.do?method=deleteTeacher";
                    $.get(url, {teaId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#tea-datagrit").datagrid("reload");// 重新加载数据库
                            $('#tea_add').dialog('close');
                        } else if (data == "NK") {
                            $.messager.alert('信息提示', '删除部分！', 'info');
                            $("#tea-datagrit").datagrid("reload");// 重新加载数据库
                            $('#tea_add').dialog('close');
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
     * Name 打开添加窗口
     */
    function openAddTeacher() {
        $('#tea-form').form('clear');
        $('#teaPassword').val('123456');
        $('#tea_add').dialog({
            closed: false,
            modal: true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    addAndUpdateTeacher('teacherServlet.do?method=addTeacher');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#tea_add').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEditTeacher() {
        $('#tea-form').form('clear');
        var rows = $('#tea-datagrit').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert("提示信息", "只能选择一行", "info");
        } else {
            var row = $('#tea-datagrit').datagrid('getSelected');
            if (row != null) {
                $('#tea_add').dialog({
                    closed: false,
                    modal: true,
                    title: "修改信息",
                    buttons: [{
                        text: '确定',
                        iconCls: 'icon-ok',
                        handler: function () {
                            addAndUpdateTeacher('teacherServlet.do?method=updateTeacher');
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#tea_add').dialog('close');
                        }
                    }]
                });
                $('#tea-form').form('load', row);
                $('#teaPassword').val('123456');
            } else {
                $.messager.alert("提示信息", "请选择修改数据", 'info');
            }
        }
    }

    /*
    * 添加和修改方法
    */
    var addAndUpdateTeacher = function (url) {
        $('#tea-form').form('submit', {
            url: url,
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('信息提示', '提交成功！', 'info');
                    $("#tea-datagrit").datagrid("reload");// 重新加载数据库
                    $('#tea_add').dialog('close');
                }
                else {
                    $.messager.alert('信息提示', '提交失败！', 'info');
                }
            }
        });
    };


    /**
     * Name 载入数据
     */
    $(function () {
        $('#tea-datagrit').datagrid({
            url: 'teacherServlet.do?method=queryTeacher',
            rownumbers: true,
            singleSelect: false,
            pageSize: 20,
            pagination: true,
            multiSort: true,
            fitColumns: true,
            fit: true,
            queryParams: formStudentJson(),
            columns: [[
                {field: '', checkbox: true},
                {field: 'teaId', title: '教师编号', width: 100, sortable: true},
                {field: 'teaRealName', title: '姓名', width: 180, sortable: true},
                {field: 'teaAccount', title: '账号', width: 100},
                //{ field:'teaPassword',title:'密码',width:100},
                {field: 'teaRemark', title: '备注', width: 100},
                {
                    field: "operation", title: '操作', width: 100,
                    formatter: function (value, row, index) {
                        return "<a onclick='grantTeaRole(" + row.teaId + ");'>设置角色</a>";
                    }
                }
            ]]
        });
    });


    /* 搜索方法*/
    $("#teasearchbtn").click(function () {
        //点击搜索
        $('#tea-datagrit').datagrid({
            queryParams: formStudentJson()
        });
    });

    /*重置方法*/
    $("#tearesetbtn").click(function () {
        $("#searchteacherform").form('clear');
        // 重新加载数据
        $('#tea-datagrit').datagrid({
            queryParams: formStudentJson()
        });
    });

    //将表单数据转为json
    function formStudentJson() {
        var teaName = $("#teasearchname").val();
        //var teaAccount = $("#teasearchclass").val();
        // 返回json
        //return {"teaName":teaName,"teaAccount":teaAccount};
        return {"teaName": teaName};
    }


    function grantTeaRole(teaId) {
        $("#grant-tea-role").dialog({
            width: 400,
            height: 300,
            top: 100,
            closed: false,
            model: true,
            title: "修改角色",
            href: 'role.do?method=initGrantTeaRole&teaId=' + teaId,
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $('#grant-role-form').form('submit', {
                        url: 'role.do?method=updateRoleForUser',
                        success: function (data) {
                            $('#grant-role-form').remove();
                            $("#grant-tea-role").dialog('close');
                            $.messager.alert('提示', '设置成功', 'icon-tip')
                        }
                    })
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $("#grant-tea-role").dialog('close')
                }
            }]
        });
    }
</script>
</body>
</html>

