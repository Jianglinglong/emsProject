<%--
  Created by IntelliJ IDEA.
  User: Direct
  Date: 2017/10/12
  Time: 20:09
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <!-- Begin of toolbar -->
        <div id="readpaper-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditAnswer()" plain="true">修改</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="readpaper-datagrit" toolbar="#readpaper-toolbar"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->
<div id="read-paper-do"></div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    function openEditAnswer() {
        var $answerId = $('#readpaper-datagrit').datagrid('getSelections');
        if ($answerId.length == 1) {
            $('#read-paper-do').dialog({
                top: 100,
                width: 800,
//                left:100,
                closed: false,
                modal: true,
                title: "添加信息",
                href: 'readPaper.do?method=getAnswerInfo&anwserID=' + $answerId[0].answerId,
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: function () {
                        $('#answer-paper-form').form('submit', {
                            url: 'readPaper.do?method=doReadPaper',
                            onSubmit: function(){
                                var isValid = $(this).form('validate');
                                return isValid;	// 返回false终止表单提交
                            },
                            success: function (data) {
                                var msg = data == "OK" ? "批改成功" : "批改失败";
                                $.messager.alert('提示', msg, 'tipinfo');
                                $('#read-paper-do').dialog('close');
                                $('#readpaper-datagrit').datagrid('reload');
                            }
                        });
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $('#read-paper-do').dialog('close');
                    }
                }]
            });
        } else {
            $.messager.alert('信息提示', '请选择一行！', 'info');
        }

    }
    /**
     * Name 载入数据
     */
    $(function () {
        $('#readpaper-datagrit').datagrid({
            url: 'readPaper.do?method=getAnswerPapaers',
            rownumbers: true,
            singleSelect: true,
            pageSize: 20,
            pagination: true,
            multiSort: true,
            fitColumns: true,
            fit: true,
            columns: [[
                {field: '', checkbox: true},
                {field: 'answerId', title: '答题卡编号', width: 100, sortable: true},
                {field: 'stuId', title: '学生ID', width: 180, sortable: true},
                {field: 'paperId', title: '试卷ID', width: 100},
            ]]
        });
    });
</script>
</body>
</html>
