<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>教师科目管理</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'center',border:false">
		    	<!-- Begin of toolbar -->
		        <div id="teacou-toolbar">
		            <div class="teacou-toolbar-button">
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddteacherCourse()" plain="true">添加</a>
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditteacherCourse()" plain="true">修改</a>
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeteacherCourse()" plain="true">删除</a>
		           		<form style="display: inline-block;" method="post" id="teachrCourseForm">
		           		课程名称: <input class="easyui-textbox" id="teacherCourseName"/>
		           		<a class="easyui-linkbutton" id="submitCourseBtn">搜索</a>
		           		<a class="easyui-linkbutton" id="resetCourseBtn">重置</a>
		           		</form>
		            </div>
		        </div>
	        <!-- End of toolbar -->
	        <table id="teacon-datagrit" toolbar="#teacou-toolbar"></table>
	    </div>
	</div>
	<!-- Begin of easyui-dialog -->

	<!-- 添加和修改页面div-start -->
	<div id="teacon_add" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
		<form id="teacon-form" method="post">
	        <table>
	        	<tr>
	        		<td><input type="hidden" name="teaCourseId"/></td>
	        	</tr>
	            <tr>
	                <td width="60" align="right">课程名称:</td>
	                <td><input id="teacher-course-combobox" type="text" name="courseId" required="required" editable="false"/></td>
	            </tr>
	            <tr>
	                <td width="60" align="right">教师姓名:</td>
	                <td><input id="teacher-name-combobox" type="text" name="teaId" required="required" editable="false"/></td>
	            </tr>
	        </table>
	    </form>
	</div>
	<!-- 添加 和修改页面div-end -->
	
<script type="text/javascript">
	
	/**
	* Name 删除记录
	*/
	function removeteacherCourse(){
		var items = $('#teacon-datagrit').datagrid('getSelections');
		if(items.length != 0){
			$.messager.confirm('信息提示','确定要删除该记录？', function(result){
				if(result){
					var ids = [];
					$(items).each(function(){
						ids.push(this.teaCourseId);	
					});
					var url = "teacherServlet.do?method=deleteTeacherCourse";
					$.get(url,{courseId:ids.toString()},function(data){
						if(data=="OK"){
							$.messager.alert('信息提示','删除成功！','info');
							$("#teacon-datagrit").datagrid("reload");// 重新加载数据库
							$('#teacon_add').dialog('close');
						}else if(data == "NO"){
							$.messager.alert('信息提示','删除部分！','info');
							$("#teacon-datagrit").datagrid("reload");// 重新加载数据库
							$('#teacon_add').dialog('close');
						}
						else{
							$.messager.alert('信息提示','删除失败！','info');
						}
					});
				}	
			});
		}else{
			$.messager.alert('信息提示','请选择要的删除数据！','info');
		}
	}
	
	/**
	* Name 打开添加窗口
	*/
	function openAddteacherCourse(){
		$('#teacon-form').form('clear');
		$('#teacon_add').dialog({
			closed: false,
			modal:true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                	addAndUpdateTeaC('teacherServlet.do?method=addTeacherCourse');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#teacon_add').dialog('close');                    
                }
            }]
        });
	}

	/**
	* Name 打开修改窗口
	*/
	function openEditteacherCourse(){
		var rows = $('#teacon-datagrit').datagrid('getSelections');
		if(rows.length > 1){
			$.messager.alert("提示信息","只能选择一行","info");
		}else{
			var row = $('#teacon-datagrit').datagrid('getSelected');
			if (row != null){
				$('#teacon_add').dialog({
					closed: false,
					modal:true,
		            title: "修改信息",
		            buttons: [{
		                text: '确定',
		                iconCls: 'icon-ok',
		                handler: function(){
		                	addAndUpdateTeaC('teacherServlet.do?method=updateTeacherCourse');
		                }
		            }, {
		                text: '取消',
		                iconCls: 'icon-cancel',
		                handler: function () {
		                    $('#teacon_add').dialog('close');                    
		                }
		            }]
		        });
				$('#teacon-form').form('load',row);
			}else{
				$.messager.alert("信息提示","请选择修改的数据","info");
			}
		}
	}
	
	/*
	* 添加和修改方法
	*/
	var addAndUpdateTeaC = function(url){
		$('#teacon-form').form('submit', {
			url:url,
			success:function(data){
				if(data=="OK"){
					$.messager.alert('信息提示','提交成功！','info');
					$("#teacon-datagrit").datagrid("reload");// 重新加载数据库
					$('#teacon_add').dialog('close');
				}
				else{
					$.messager.alert('信息提示','提交失败！','info');
				}
			}
		}); 
	};
	
	


	
	/**
	* Name 载入数据
	*/
	$(function(){
		$('#teacon-datagrit').datagrid({
			url:'teacherServlet.do?method=showTeacherScoure',
			rownumbers:true,
			singleSelect:false,
			pageSize:20,           
			pagination:true,
			multiSort:true,
			fitColumns:true,
			fit:true,
			queryParams: formTeacherCourseJson(),// 在请求远程数据的时候发送额外的参数。
//			rowStyler:function(index,row){
//		        if (row.teaCourseId > 0){
//		            return 'background-color:#fff;'; // 为教师课程行设置颜色
//		        }
//		    },
			columns:[[
				{ field:'',checkbox:true},
				{ field:'teaCourseId',title:'教师课程关系编号',width:100,hidden:true},
				{ field:'courseId',title:'课程编号',width:180,hidden:true},
				{ field:'courseName',title:'课程名称',width:100,},
				{ field:'teaName',title:'教师名称',width:100,},
				{ field:'teaCourseId',title:'教师编号',width:100,hidden:true},
			]]
		});
	});
	$('#teacher-course-combobox').combobox({
        url:'teacherServlet.do?method=getCourseJson',
        valueField:'id',
        textField:'name',
        panelMaxHeight:'100'
    });
	$('#teacher-name-combobox').combobox({
	    url:'teacherServlet.do?method=getTeacherCJson',
	    valueField:'id',
	    textField:'name',
	    panelMaxHeight:'100'
	});
	
	
	/* 搜索方法*/
 	$("#submitCourseBtn").click(function(){
 		//点击搜索
 		$('#teacon-datagrit').datagrid({ 
 			queryParams: formTeacherCourseJson()
 		});   
 	}); 
	
	/*重置方法*/
 	$("#resetCourseBtn").click(function(){
 		$("#teachrCourseForm").form('clear');
 		// 重新加载数据
 		$('#teacon-datagrit').datagrid({ 
 				queryParams: formTeacherCourseJson()
 		}); 
 	});

    //将表单数据转为json
    function formTeacherCourseJson() {
    	var teacherCourseName = $("#teacherCourseName").val();
    	// 返回json
        return {"teacherCourseName":teacherCourseName};
    }
	
	
	
</script>
</body>
</html>