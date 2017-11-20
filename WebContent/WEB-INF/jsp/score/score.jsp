<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>成绩管理</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'center',border:false">
		    	<!-- Begin of toolbar -->
		        <div id="sc-toolbar" style="align:center;">
		            <div class="wu-toolbar-button">
		               <!--  <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddScore()" plain="true">添加</a>
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditScore()" plain="true">修改</a>
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeScore()" plain="true">删除</a> -->
		                <form style="width: 100%" name="searchscoreform" method="post" action="" id ="searchscoreform">
						姓名:<input id="scoresearchname"  class="easyui-textbox"  name="scoreName"/>
						科目:<input id="scoresearchclass" class="easyui-textbox"  name="scoreCourse"/>
						<a id="scoresearchbtn" class="easyui-linkbutton">搜索</a>
						<a id="scoreresetbtn" class="easyui-linkbutton">重置</a>
						</form>
		            </div>
		        </div>
	        	<!-- End of toolbar -->
	        <table id="sc-datagrit" toolbar="#sc-toolbar"></table>
	    </div>
	</div>
	
	<!-- Begin of easyui-dialog -->
<!-- 添加和修改页面div-start -->
<div id="sc_add" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'"
     style="width:400px; padding:10px;">
    <form id="sc-form" method="post">
        <table>
        	<tr>
        		<td colspan="2"><input type="hidden" name="scoreId"/></td>
        	</tr>
            <tr>
                <td align="right">学生编号:</td>
                <td><input type="text" name="stuId" class="easyui-textbox" /></td>
            </tr>
            <tr>
                <td align="right">试卷编号:</td>
                <td><input type="text" name="paperId" class="easyui-textbox" /></td>
            </tr>
            <tr>
                <td align="right">科目编号:</td>
                <td><input type="text" name="courseId" class="easyui-textbox" /></td>
            </tr>
            <tr>
                <td valign="top" align="right">客观成绩:</td>
                <td><input type="text" id="autoScore" name="autoScore" class="easyui-textbox" /></td>
            </tr>
            <tr>
                <td valign="top" align="right">主观成绩:</td>
                <td><input type="text" id="subScore" name="subScore" class="easyui-textbox" /></td>
            </tr>
            <tr>
                <td valign="top" align="right">总成绩:</td>
                <td><input type="text" id="totalScore" name="totalScore" class="easyui-textbox" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- 添加 和修改页面div-end -->

<script type="text/javascript">
	// 总成绩
	$("#totalScore").ready(function(){
		var auto = $("#autoScore").val();
		var sub = $("#subScore").val();
		$("#totalScore").val(auto+sub);
	});
	
</script>
<!-- End of easyui-dialog -->
<script type="text/javascript">

    /**
     * Name 删除记录
     */
    function removeScore() {
        var items = $('#sc-datagrit').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.scoreId);
                    });
                    var url = "scoreServlet.do?method=deleteScore";
                    $.get(url, {scoreId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $("#sc-datagrit").datagrid("reload");// 重新加载数据库
                            $('#sc_add').dialog('close');
                        } else if (data == "NK") {
                            $.messager.alert('信息提示', '删除部分！', 'info');
                            $("#sc-datagrit").datagrid("reload");// 重新加载数据库
                            $('#sc_add').dialog('close');
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
    function openAddScore() {
        $('#sc-form').form('clear');
        $('#sc_add').dialog({
            closed: false,
            modal: true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function () {
                    addAndScoreUpdate('scoreServlet.do?method=addScore');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#sc_add').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEditScore() {
        var rows = $('#sc-datagrit').datagrid('getSelections');
        if (rows.length > 1) {
            $.messager.alert("提示信息", "只能选择一行", "info");
        } else {
            var row = $('#sc-datagrit').datagrid('getSelected');
            if (row != null) {
                $('#sc_add').dialog({
                    closed: false,
                    modal: true,
                    title: "修改信息",
                    buttons: [{
                        text: '确定',
                        iconCls: 'icon-ok',
                        handler: function () {
                            addAndScoreUpdate('scoreServlet.do?method=updateScore');
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#sc_add').dialog('close');
                        }
                    }]
                });
                $('#sc-form').form('load', row);
            } else {
                $.messager.alert("信息提示", "请选择修改的数据", "info");
            }
        }
    }

    /*
    * 添加和修改方法
    */
    var addAndScoreUpdate = function (url) {
        $('#sc-form').form('submit', {
            url: url,
            success: function (data) {
                if (data == "OK") {
                    $.messager.alert('信息提示', '提交成功！', 'info');
                    $("#sc-datagrit").datagrid("reload");// 重新加载数据库
                    $('#sc_add').dialog('close');
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
        $('#sc-datagrit').datagrid({
            url: 'scoreServlet.do?method=queryScore',
            rownumbers: true,
            singleSelect: false,
            pageSize: 20,
            pagination: true,
            multiSort: true,
            fitColumns: true,
            fit: true,
            queryParams: formJson(),// 在请求远程数据的时候发送额外的参数。
            rowStyler:function(index,row){    
    	        if (row.scoreId > 0){    
    	            return 'background-color:#fff;'; // 为成绩行设置颜色   
    	        }    
    	    }, 
            columns: [[
            	{field: '',checkbox:true},
                {field: 'scoreId', title: '成绩编号', width: 100, sortable: true},
                {field: 'stuId', title: '学生姓名', width: 180, sortable: true,formatter:function(value,row,index){
	            	$.ajaxSettings.async =false;
	            	var courseName ="";
	            	$.get('studentServlet.do?method=getStudentNameById',{'stuId':value},function(data){
		            	courseName = data;
	            	});
	            	$.ajaxSettings.async =true;
	            	return courseName;
	            }},
                {field: 'paperId', title: '试卷编号', width: 100},
                {field: 'courseId', title: '科目', width: 100,formatter:function(value,row,index){
	            	$.ajaxSettings.async =false;
	            	var courseName ="";
	            	$.get('courseServlet.do?method=getCourseNameById',{'courseId':value},function(data){
		            	courseName = data;
	            	});
	            	$.ajaxSettings.async =true;
	            	return courseName;
	            }},
                {field: 'autoScore', title: '客观分数', width: 100},
                {field: 'subScore', title: '主观分数', width: 100},
                {field: 'totalScore', title: '总分数', width: 100}
            ]]
        });
    });
    
    
    

	/* 搜索方法*/
 	$("#scoresearchbtn").click(function(){
 		//点击搜索
 		$('#sc-datagrit').datagrid({ 
 			queryParams: formJson()
 		});   
 	}); 
	
	/*重置方法*/
 	$("#scoreresetbtn").click(function(){
 		$("#searchscoreform").form('clear');
 		// 重新加载数据
 		$('#sc-datagrit').datagrid({ 
 				queryParams: formJson()
 			}); 
 	});

    //将表单数据转为json
    function formJson() {
    	var scoreName = $("#scoresearchname").val();
    	var scoreCourse = $("#scoresearchclass").val();
    	// 返回json
        return {"scoreName":scoreName,"scoreCourse":scoreCourse};
    }
    
    

</script>
</body>
</html>

