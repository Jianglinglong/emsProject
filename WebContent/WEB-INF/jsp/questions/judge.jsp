<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="judge-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddJudge()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openJudgeEdit()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="openRemoveJudge()"
                   plain="true">删除</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="openImportJudge()"
                   plain="true">导入</a>
                <form id="judge-search-form" style="display: inline-block">
                    科目：<input class="easyui-textbox" id="judge-course-value"/>
                    科目：<input type="date" id="daytimejudge-course-value"/>
                    <a id="judge-search-btn" class="easyui-linkbutton">搜索</a>
                    <a id="judge-search-reset" class="easyui-linkbutton">重置</a>
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="judge-datagrid" toolbar="#judge-toolbar"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加修改页面 -->
<div id="judge-dialog"
     style="width:400px; padding:10px;">
    <form id="judge-form" method="post">
        <table>
            <tr>
                <td><input type="hidden" name="judgeId" /></td>
            </tr>
            <tr>
                <td width="60" align="right">课程</td>
                <td><input type="text" name="courseId" id="courseId"required="required" class="easyui-textbox"editable="false" panelMaxHeight="100"/></td>
            </tr>
            <tr>
                <td align="right">题目</td>
                <td>
                    <textarea name="judgeTitle" rows="3" cols="28"></textarea>

            </tr>
            <tr>
                <td align="right">正确答案</td>
                <td><input type="text" name="answer" class="easyui-textbox"/></td>
            </tr>
            
            <%--<tr>--%>
                <%--<td align="right">时间</td>--%>
                <%--<td><input type="text" name="daytime" class="easyui-textbox"/></td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>
<div id="judge-import-dialog"class="easyui-dialog" data-options="closed:true" style="padding: 30px" >
    <form id="judge-import-form" method="post" enctype="multipart/form-data">
        <input id="judge-import-form-input" name="excel" style="width:300px">
    </form>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    //导入
    function openImportJudge() {
        $('#judge-import-form').form('clear');
        $('#judge-import-dialog').dialog({
            title: '判断题导入',
            width: 400,
            height: 200,
            closed: false,
            modal: true,
            buttons: [{
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#judge-import-form").form('submit', {
                        url: 'judge.do?method=importExcel',
                        onSubmit: function () {
                            var validate =  $(this).form('validate');
                            if (validate){
                                $.messager.progress({
                                    title: '提示',
                                    msg: '正在导入...',
                                    text: '导入中',
                                });
                            }

                            return validate;
                        },
                        success: function (data) {
                            $.messager.progress('close');
                            if (data == "OK") {
                                $.messager.alert('信息提示', '提交成功！');
                                $('#judge-import-dialog').dialog('close');
                                $('#judge-datagrid').datagrid('reload');
                            }

                        }

                    });
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#judge-import-dialog').dialog('close');
                }
            }]
        });
    }

    $(function () {
        $('#judge-import-form-input').filebox({
            buttonText: '选择导入的excel文件',
            buttonAlign: 'right',
            height: 40,
        })
    });


    /**
     * Name 删除记录
     */
    function openRemoveJudge() {
        var items = $('#judge-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.judgeId);
                    });
                    var url = 'judge.do?method=deleteJudge';
                    $.get(url, {stuId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#judge-datagrid").datagrid("reload");// 重新加载数据库
                            $('#judge-dialog').dialog('close');
                        } else if (data == "NO") {
                            $.messager.alert('信息提示', '删除部分！', 'info');
                            $('#judge-dialog').dialog('close');
                        }
                        else {
                            $.messager.alert('信息提示', '删除失败！', 'info');
                        }
                    });
                }
            });
        }
    }

    /**
     * Name 打开添加窗口
     */
    function openAddJudge() {
        $('#judge-form').form('clear');
        $('#judge-dialog').dialog({
            closed: false,
            modal: true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#judge-form").form('submit', {
                        url: 'judge.do?method=addJudge',
                        onSubmit: function () {

                        },
                        success: function (data) {
                            if (data == "OK") {
                                $.messager.alert('信息提示', '提交成功！');
                                $("#judge-datagrid").datagrid("reload");// 重新加载数据库
                                $('#judge-dialog').dialog('close');
                            }
                            else {
                                $.messager.alert('信息提示', '提交失败！');
                            }
                        }

                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#judge-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openJudgeEdit() {
        var rows = $('#judge-datagrid').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert("提示信息", "只能选择一行");
        } else if (rows.length > 0) {
            $('#judge-dialog').dialog({
                closed: false,
                modal: true,
                title: "修改信息",
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $('#judge-form').form('submit', {
                            url: 'judge.do?method=updateJudge',
                            success: function (data) {
                                if (data == "OK") {
                                    $.messager.alert('信息提示', '修改成功！');
                                    $("#judge-datagrid").datagrid("reload");// 重新加载数据库
                                    $('#judge-dialog').dialog('close');
                                }
                                else {
                                    $.messager.alert('信息提示', '修改失败！');
                                }
                            }
                        });
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $('#judge-dialog').dialog('close');
                    }
                }]
            });
            $('#judge-form').form('load', rows[0]);
        } else {
            $.messager.alert('信息提示', '请选择修改对象！');
        }
    }


    /**
     * Name 载入数据
     */
    $('#judge-datagrid').datagrid({
        url: 'judge.do?method=queryJudge',
        queryParams: formJudgeJson(),
        rownumbers: true,
        singleSelect: false,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        fit: true,
        columns: [[
            {field: '', checkbox: true},
            {field: 'judgeId', title: '编号', width: 50, sortable: true, hidden: true},
            {field: 'courseId', title: '科目', width: 50, sortable: true,formatter:function(value,row,index) {
                $.ajaxSettings.async = false;
                var courseName = "";
                $.get('courseServlet.do?method=getCourseNameById', {'courseId': value}, function (data) {
                    courseName = data;
                });
                $.ajaxSettings.async = true;
                return courseName;
            }},
            {field: 'judgeTitle', title: '题目', width: 180, sortable: true},
            {field: 'answer', title: '正确答案', width: 100},
            {field: 'daytime', title: '时间(xx-年xx-月xx-日) ', width: 100}
            
        ]]
    });





    
    /* 搜索方法*/
    $("#judge-search-btn").click(function () {
        //点击搜索
        $('#judge-datagrid').datagrid({
            queryParams: formJudgeJson()
        });
    });

    /*重置方法*/
    $("#judge-search-reset").click(function () {
        $("#judge-search-form").form('clear');
        $("daytimejudge-course-value").val('')
        // 重新加载数据
        $('#judge-datagrid').datagrid({
            queryParams: formJudgeJson()
        });
    });

    //将表单数据转为json
    function formJudgeJson() {
        var bJudgeName = $("#judge-course-value").val();
        var daytimejudge=$("daytimejudge-course-value").val();
        return {"bJudgeName":bJudgeName,daytimejudge:daytimejudge};
    }

    /**
     * 创建课程的下拉框
     */
    $('#courseId').combobox({
        url: 'courseServlet.do?method=getCourseByUserId',
        valueField: 'id',
        textField: 'name',
        panelMaxHeight: '100',
    });
</script>
</body>
</html>