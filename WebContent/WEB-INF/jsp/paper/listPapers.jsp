<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../../common/jstl.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'center',border:false">
	        <!-- Begin of toolbar -->
	        <div id="list-papers-toolbar">
	            <div class="wu-toolbar-button">
	                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddListPaper()" plain="true">添加</a>
	                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditListPaper()" plain="true">修改</a>
	                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeListPaper()" plain="true">删除</a>
	                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-search" onclick="showListPaper()" plain="true">查看</a>
	            </div>
	
	        </div>
	        <!-- End of toolbar -->
	        <table id="paper-list-datagrid" toolbar="#list-papers-toolbar"></table>
	    </div>
	</div>
	
	<!-- Begin of easyui-dialog -->

	<!-- 添加修改页面 -->
	<div id="listPaper_add" style="width:400px; padding:10px;">
	    <form id="paper-list-form" method="post">
	        <table>
	            <tr>
	                <td><input type="hidden" name="paperID" /></td>
	            </tr>
	            <tr>
	                <td width="80" align="right">科目名称</td>
	                <td>
	                	<input id="coursIDPaper" name="courseID" required="required" value="请选择科目"  editable="false">
	                </td>
	            </tr>
	            <tr>
	                <td align="right">试卷规则名</td>
	                <td>
						<input id="ruleID" name="ruleID" required="required" class="easyui-combobox" data-options="valueField:'id',textField:'name'" editable="false" panelMaxHeight="100"/>  
	                </td>
	            </tr>
	            <tr>
	                <td align="right">试卷名</td>
	                <td>
						<input id="paperName" name="paperName" required="required" class="easyui-textbox"/>  
	                </td>
	            </tr>
	            <tr>
	                <td align="right">考试时间</td>
	                <td><input type="text" name="paperTime" class="easyui-textbox"/></td>
	            </tr>
	        </table>
	    </form>
	</div>
	
	
	<!-- End of easyui-dialog -->
	<script>
	 /**
     * Name 删除记录
     */
    function removeListPaper() {
        var items = $('#paper-list-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.paperID)
                    });
                    var url = 'paper.do?method=deletePaper';
                    $.get(url, {paperId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#paper-list-datagrid").datagrid("reload");// 重新加载数据库
                        } else if (data == "NO") {
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
    function openAddListPaper() {
        $('#paper-list-form').form('clear');
        $('#ruleID').combobox('reload');
        $('#listPaper_add').dialog({
        	closed:false,
            modal: true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function() {
                    $("#paper-list-form").form('submit',{
                    	url:'paper.do?method=addPaper',
                    	onSubmit:function(){
                    		var isValid = $(this).form('validate');
                            return isValid;	// 返回false终止表单提交
                    	},
                    	success:function(data){
                    		if (data == "OK") {
                                $.messager.alert('信息提示', '提交成功！');
                                $("#paper-list-datagrid").datagrid("reload");// 重新加载数据库
                                $('#listPaper_add').dialog('close');
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
                    $('#listPaper_add').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEditListPaper() {
    	$('#paper-list-form').form('clear');
        var rows = $('#paper-list-datagrid').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert("提示信息", "只能选择一行!");
        } else if(rows.length > 0 ){

           $('#listPaper_add').dialog({
               closed: false,
               modal: true,
               title: "修改信息",
               buttons: [{
                   text: '确定',
                   iconCls: 'icon-ok',
                   handler: function () {
                	   
                       $('#paper-list-form').form('submit', {
                           url: 'paper.do?method=updatePaper',
                   		   onSubmit:function(){
                   			   
                   		   },
                           success: function (data) {
                               if (data == "OK") {
                                   $.messager.alert('信息提示', '修改成功！');
                                   $("#paper-list-datagrid").datagrid("reload");// 重新加载数据库
                                   $('#listPaper_add').dialog('close');
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
                       $('#listPaper_add').dialog('close');
                   }
               }]
           });
           $('#paper-list-form').form('load',rows[0]);
           var url = 'paper.do?method=getPaperRuleJson&coursId='+rows[0].courseID;    
           $('#ruleID').combobox('reload', url);
           $('#ruleID').combobox('select', rows[0].ruleID);
           
        } else {
        	$.messager.alert('信息提示', '请选择修改对象！');
        }
    }

    /**
   	 *	查看试卷信息
     */
     function showListPaper(){
     	var rows = $('#paper-list-datagrid').datagrid('getSelections');
         if (rows.length > 1) {
             $.messager.alert("提示信息", "只能选择一行!");
         } else if(rows.length > 0 ){
         	window.open("paper.do?method=initPaper&paperId="+rows[0].paperID);
         } else {
         	$.messager.alert('信息提示', '请选择查看对象！');
         }
     	
     }
   

    /**
     * Name 载入数据
     */
    $('#paper-list-datagrid').datagrid({
        url: 'paper.do?method=jsonPaperList',
        rownumbers: true,
        singleSelect: false,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        fit: true,
        columns: [[
            {field:'',checkbox: true},
            {field: 'paperID', title: '试卷编号', width: 50, sortable: true},
            {field: 'courseID', title: '课程名', width: 80, sortable: true,formatter:function(value,row,index){
            	$.ajaxSettings.async = false;
            	var courseName ="";
            	$.get('courseServlet.do?method=getCourseNameById',{'courseId':value},function(data){
            		courseName = data;
            	});
            	$.ajaxSettings.async = true;
            	return courseName;
            }},
            {field: 'paperName', title: '试卷名', width: 150, sortable: true},
            {field: 'ruleID', title: '试卷规则名', width: 150, sortable: true,formatter:function(value,row,index){
            	$.ajaxSettings.async = false;
            	var ruleName ="";
            	$.get('paper.do?method=getPaperRuleNameByRuleId',{'ruleId':value},function(data){
            		ruleName = data;
            	});
            	$.ajaxSettings.async = true;
            	return ruleName;
            }},
            {field: 'paperTime', title: '考试时间', width: 100}
        ]]
    });
	
  	/**
  	 * 创建课程的下拉框
  	 */
  	 
  	$('#coursIDPaper').combobox({
  	    url:'exam.do?method=getCourseJson',    
  	    valueField:'id',    
  	    textField:'name',
  	  	panelMaxHeight:'100',
  	    onSelect:function(rec){
  	    	$('#ruleID').combobox('select', "");
  	    	var url = 'paper.do?method=getPaperRuleJson&coursId='+rec.id;    
            $('#ruleID').combobox('reload', url);
  	    }
  	});  
	
	</script>
</body>
</html>