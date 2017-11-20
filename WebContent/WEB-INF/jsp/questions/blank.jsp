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
        <div id="blank-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddBlank()"
                   plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditBlank()"
                   plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="openRemoveBlank()"
                   plain="true">删除</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-ok" onclick="openImportBlank()"
                   plain="true">导入</a>
                
                <form id="blank-search-form" style="display: inline-block">
                     科目：<input class="easyui-textbox" id="blank-course-value"/>
                     时间：<input type="date" id="daytimeblank-course-value"/>
                    <a id="blank-search-btn" class="easyui-linkbutton">搜索</a>
                    <a id="blank-search-reset" class="easyui-linkbutton">重置</a>
                </form>
          
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="blank-datagrid" toolbar="#blank-toolbar"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加修改页面 -->
<div id="blank-dialog" style="width:400px; padding:10px;">
    <form id="blank-form" method="post">
        <table>
            <tr>
                <td><input type="hidden" name="blankId"/></td>
            </tr>
            <tr>
                <td width="60" align="right">课程</td>
                <td><input type="text" name="courseId" id="courseId"required="required" editable="false" panelMaxHeight="100"/></td>
            </tr>
            <tr>
                <td align="right">题目</td>
                <td>
                    <textarea name="blankTitle" rows="3" cols="28"></textarea>

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
<div id="blank-import-dialog" class="easyui-dialog" data-options="closed:true" style="padding: 30px" >
    <form id="blank-import-form" method="post" enctype="multipart/form-data">
        <input id="fb" name="excel" style="width:300px">
    </form>
</div>

</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    //导入
    function openImportBlank() {
        $('#blank-import-form').form('clear');
        $('#blank-import-dialog').dialog({
            title: '填空题导入',
            width: 400,
            height: 200,
            closed: false,
            modal: true,
            buttons: [{
                text: '保存',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#blank-import-form").form('submit', {
                        url: 'blank.do?method=importExcel',
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
                            $('#blank-import-dialog').dialog('close');
                            $('#blank-datagrid').datagrid('reload');
                        }

                    });
                }
            }, {
                text: '关闭',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#blank-import-dialog').dialog('close');
                }
            }]
        });
    }

    $(function () {
        $('#fb').filebox({
            buttonText: '选择导入的excel文件',
            buttonAlign: 'right',
            height: 40,
        })
    });

    /**
     * Name 删除记录
     */
    function openRemoveBlank() {
        var items = $('#blank-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.blankId);
                    });
                    var url = 'blank.do?method=deleteBlank';
                    $.get(url, {stuId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#blank-datagrid").datagrid("reload");// 重新加载数据库
                            $('#blank-dialog').dialog('close');
                        } else if (data == "NO") {
                            $.messager.alert('信息提示', '删除部分！', 'info');
                            $('#blank-dialog').dialog('close');
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
    function openAddBlank() {
        $('#blank-form').form('clear');
        $('#blank-dialog').dialog({
            closed: false,
            modal: true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    $("#blank-form").form('submit', {
                        url: 'blank.do?method=addBlank',
                        onSubmit: function () {

                        },
                        success: function (data) {
                            if (data == "OK") {
                                $.messager.alert('信息提示', '提交成功！');
                                $("#blank-datagrid").datagrid("reload");// 重新加载数据库
                                $('#blank-dialog').dialog('close');
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
                    $('#blank-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEditBlank() {
        var rows = $('#blank-datagrid').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert("提示信息", "只能选择一行");
        } else if (rows.length > 0) {
            $('#blank-dialog').dialog({
                closed: false,
                modal: true,
                title: "修改信息",
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $('#blank-form').form('submit', {
                            url: 'blank.do?method=updateBlank',
                            success: function (data) {
                                if (data == "OK") {
                                    $.messager.alert('信息提示', '修改成功！');
                                    $("#blank-datagrid").datagrid("reload");// 重新加载数据库
                                    $('#blank-dialog').dialog('close');
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
                        $('#blank-dialog').dialog('close');
                    }
                }]
            });
            $('#blank-form').form('load', rows[0]);
        } else {
            $.messager.alert('信息提示', '请选择修改对象！');
        }
    }


    /**
     * Name 载入数据
     */
    $('#blank-datagrid').datagrid({
        url: 'blank.do?method=queryBlank',

        rownumbers: true,
        singleSelect: false,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        queryParams: formBlankJson(),
        fit: true,
        columns: [[
            {field: '', checkbox: true},
            {field: 'blankId', title: '编号', width: 50, sortable: true, hidden: true},
            {field: 'courseId', title: '科目', width: 50, sortable: true,formatter:function(value,row,index) {
                $.ajaxSettings.async = false;
                var courseName = "";
                $.get('courseServlet.do?method=getCourseNameById', {'courseId': value}, function (data) {
                    courseName = data;
                });
                $.ajaxSettings.async = true;
                return courseName;
            }},
            {field: 'blankTitle', title: '题目', width: 180, sortable: true},
            {field: 'answer', title: '正确答案', width: 100},
            {field: 'daytime', title: '时间(xx-年xx-月xx-日) ', width: 100}
        ]]
    });


    
    
    

    /* 搜索方法*/
    $("#blank-search-btn").click(function(){
        //点击搜索
        $('#blank-datagrid').datagrid({
            queryParams: formBlankJson()
        });
    });

    /*重置方法*/
    $("#blank-search-reset").click(function(){
        $("#blank-search-form").form('clear');
        $("#daytimeblank-course-value").val('');
        // 重新加载数据
        $('#blank-datagrid').datagrid({
            queryParams: formBlankJson()
        });
    });

    //将表单数据转为json
    function formBlankJson() {
        var bCourseName = $("#blank-course-value").val();
        var daytimeblank = $("#daytimeblank-course-value").val();
        return {"bCourseName":bCourseName,daytimeblank:daytimeblank};
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