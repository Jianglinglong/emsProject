<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html >
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="Subjective-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openSubAdd()" plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openSubEdit()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="opernRemoveSub()"
                   plain="true">删除</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="opernImportSub()"
                   plain="true">导入</a>
                <form id="sub-search-form" style="display: inline-block">
                    科目：<input class="easyui-textbox" id="sub-course-value"/>
                    时间：<input type="date" id="dayitmesub-course-value"/>
                    
                    <a id="sub-search-btn" class="easyui-linkbutton">搜索</a>
                    <a id="sub-search-reset" class="easyui-linkbutton">重置</a>
                </form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="subjective-datagrid" toolbar="#Subjective-toolbar"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加修改页面 -->
<div id="subjective-dialog" style="width:400px; padding:10px;">
    <form id="subjective-form" method="post">
        <table>
            <tr>
                <td><input type="hidden" name="subId" /></td>
            </tr>
            <tr>
                <td width="60" align="right">课程</td>
                <td><input type="text" name="courseId" id="courseId"required="required" editable="false" panelMaxHeight="100"/></td>
            </tr>
            <tr>
                <td align="right">题目</td>

                <td>
                    <textarea name="subTitle" rows="3" cols="28"></textarea>

            </tr>
            <tr>
                <td align="right">正确答案</td>
                <td>
                    <textarea name="answer" rows="3" cols="28"></textarea>
            </tr>
            <%--<tr>--%>
                <%--<td align="right">时间</td>--%>
                <%--<td><input type="text" name="daytime" class="easyui-textbox"/></td>--%>
            <%--</tr>--%>
        </table>
    </form>
</div>
<div id="sub-import-dialog"class="easyui-dialog" data-options="closed:true" style="padding: 30px">
    <form id="sub-import-form" method="post" enctype="multipart/form-data">
        <input id="sub-import-input" name="excel" style="width:300px">
    </form>
</div>


<!-- End of easyui-dialog -->
<script type="text/javascript">

    //导入
    function opernImportSub() {
        $('#sub-import-form').form('clear');
        $('#sub-import-dialog').dialog({
            title: '主观题导入',
            width: 400,
            height: 200,
            closed: false,
            modal: true,
            buttons: [{
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#sub-import-form").form('submit', {
                        url: 'subjective.do?method=importExcel',
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
                            }
                            $('#sub-import-dialog').dialog('close');
                            $('#subjective-datagrid').datagrid('reload');
                        }

                    });
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#sub-import-dialog').dialog('close');
                }
            }]
        });
    }

    $(function () {
        $('#sub-import-input').filebox({
            buttonText: '选择导入的excel文件',
            buttonAlign: 'right',
            height: 40,
        })
    });

    /**
     * Name 删除记录
     */
    function opernRemoveSub() {
        var items = $('#subjective-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.subId);
                    });
                    var url = 'subjective.do?method=deleteSubjective';
                    $.get(url, {stuId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#subjective-datagrid").datagrid("reload");// 重新加载数据库
                            $('#subjective-dialog').dialog('close');
                        } else if (data == "NO") {
                            $.messager.alert('信息提示', '删除部分！', 'info');
                            $('#subjective-dialog').dialog('close');
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
    function openSubAdd() {
        $('#subjective-form').form('clear');
        $('#subjective-dialog').dialog({
            closed: false,
            modal: true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#subjective-form").form('submit', {
                        url: 'subjective.do?method=addSubjective',
                        onSubmit: function () {

                        },
                        success: function (data) {
                            if (data == "OK") {
                                $.messager.alert('信息提示', '提交成功！');
                                $("#subjective-datagrid").datagrid("reload");// 重新加载数据库
                                $('#subjective-dialog').dialog('close');
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
                    $('#subjective-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openSubEdit() {
        var rows = $('#subjective-datagrid').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert("提示信息", "只能选择一行");
        } else if (rows.length > 0) {
            $('#subjective-dialog').dialog({
                closed: false,
                modal: true,
                title: "修改信息",
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $('#subjective-form').form('submit', {
                            url: 'subjective.do?method=updateSubjective',
                            success: function (data) {
                                if (data == "OK") {
                                    $.messager.alert('信息提示', '修改成功！');
                                    $("#subjective-datagrid").datagrid("reload");// 重新加载数据库
                                    $('#subjective-dialog').dialog('close');
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
                        $('#subjective-dialog').dialog('close');
                    }
                }]
            });
            $('#subjective-form').form('load', rows[0]);
        } else {
            $.messager.alert('信息提示', '请选择修改对象！');
        }
    }


    /**
     * Name 载入数据
     */
    $('#subjective-datagrid').datagrid({
        url: 'subjective.do?method=sbujectiveQuery',
        queryParams: formSubJson(),
        rownumbers: true,
        singleSelect: false,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        fit: true,
        columns: [[
            {field: '', checkbox: true},
            {field: 'subId', title: '编号', width: 50, sortable: true, hidden: true},
            {field: 'courseId', title: '科目', width: 50, sortable: true,formatter:function(value,row,index) {
                $.ajaxSettings.async = false;
                var courseName = "";
                $.get('courseServlet.do?method=getCourseNameById', {'courseId': value}, function (data) {
                    courseName = data;
                });
                $.ajaxSettings.async = true;
                return courseName;
            }},
            {field: 'subTitle', title: '题目', width: 180, sortable: true},
            {field: 'answer', title: '正确答案', width: 100},
            {field: 'daytime', title: '时间(xx-年xx-月xx-日) ', width: 100}
        ]]
    });
    /* 搜索方法*/
    $("#sub-search-btn").click(function () {
        //点击搜索
        $('#subjective-datagrid').datagrid({
            queryParams: formSubJson()
        });
    });

    /*重置方法*/
    $("#sub-search-reset").click(function () {
        $("#sub-search-form").form('clear');
        $("#dayitmesub-course-value").val('');

        // 重新加载数据
        $('#subjective-datagrid').datagrid({
            queryParams: formSubJson()
        });
    });

    //将表单数据转为json
    function formSubJson() {
        var bSubName = $("#sub-course-value").val();
        var daytimesub = $("#dayitmesub-course-value").val();
        return {"bSubName": bSubName,daytimesub:daytimesub};
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