<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>科目管理</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'center',border:false">
		    	<!-- Begin of toolbar -->
		        <div id="cou-toolbar">
		            <div class="cou-toolbar-button">
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddCourse()" plain="true">添加</a>
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditCourse()" plain="true">修改</a>
		                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeCourse()" plain="true">删除</a>
		                <form style="display: inline-block;" method="post" id ="searchCourseForm">
						课程名称: <input id="courseSearchName" class="easyui-textbox"/>
						<a id="courseSearchBtn" class="easyui-linkbutton">搜索</a>
						<a id="courseResetBtn" class="easyui-linkbutton">重置</a>
						</form>
		            </div>
		        </div>
	        <!-- End of toolbar -->
	        <table id="con-datagrit" toolbar="#cou-toolbar"></table>
	    </div>
	</div>
	<!-- Begin of easyui-dialog -->

	<!-- 添加和修改页面div-start -->
	<div id="con_add" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
		<form id="con-form" method="post">
	        <table>
	        	<tr>
	        		<td><input type="hidden" name="courseId"/></td>
	        	</tr>
	            <tr>
	                <td width="60" align="right">课程名称:</td>
	                <td><input type="text" name="courseName" required="required" class="easyui-textbox" /></td>
	            </tr>
	            <tr style="display:none;">
	                <td align="right" >是否启用:</td>
	                <td>
	                	<select name="enable" required="required" class="easyui-combobox" editable="false" style="width:134px;" panelMaxHeight="100">
								<option value="是" selected>是</option>
								<option value="否" >否</option>
						</select>
	                </td>
	            </tr>
	        </table>
	    </form>
	</div>
	<!-- 添加 和修改页面div-end -->
	
<script type="text/javascript">
	
	/**
	* Name 删除记录
	*/
	function removeCourse(){
		var items = $('#con-datagrit').datagrid('getSelections');
		if(items.length != 0){
			$.messager.confirm('信息提示','确定要删除该记录？', function(result){
				if(result){
					var ids = [];
					$(items).each(function(){
						ids.push(this.courseId);	
					});
					var url = "courseServlet.do?method=deleteCourse";
					$.get(url,{courseId:ids.toString()},function(data){
						if(data=="OK"){
							$.messager.alert('信息提示','删除成功！','info');
							$("#con-datagrit").datagrid("reload");// 重新加载数据库
							$('#con_add').dialog('close');
						}else if(data == "NK"){
							$.messager.alert('信息提示','删除部分！','info');
							$("#con-datagrit").datagrid("reload");// 重新加载数据库
							$('#con_add').dialog('close');
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
	function openAddCourse(){
		$('#con-form').form('clear');
		$('#con_add').dialog({
			closed: false,
			modal:true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                	addAndUpdate('courseServlet.do?method=addCourse');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#con_add').dialog('close');                    
                }
            }]
        });
	}

	/**
	* Name 打开修改窗口
	*/
	function openEditCourse(){
		var rows = $('#con-datagrit').datagrid('getSelections');
		if(rows.length > 1){
			$.messager.alert("提示信息","只能选择一行","info");
		}else{
			var row = $('#con-datagrit').datagrid('getSelected');
			if (row != null){
			var courseName = row['courseName'];
				$('#con_add').dialog({
					closed: false,
					modal:true,
		            title: "修改信息",
		            buttons: [{
		                text: '确定',
		                iconCls: 'icon-ok',
		                handler: function(){
		                	addAndUpdate('courseServlet.do?method=updateCourse&courseName='+courseName);
		                }
		            }, {
		                text: '取消',
		                iconCls: 'icon-cancel',
		                handler: function () {
		                    $('#con_add').dialog('close');                    
		                }
		            }]
		        });
				$('#con-form').form('load',row);
			}else{
				$.messager.alert("信息提示","请选择修改的数据","info");
			}
		}
	}
	
	/*
	* 添加和修改方法
	*/
	var addAndUpdate = function(url){
		$('#con-form').form('submit', {
			url:url,
			success:function(data){
				if(data=="OK"){
					$.messager.alert('信息提示','提交成功！','info');
					$("#con-datagrit").datagrid("reload");// 重新加载数据库
					$('#con_add').dialog('close');
				}else if(data=="NK"){
					$.messager.alert('信息提示','请输入新的课程名！','warning');
				}
				else if(data=="ON"){
					$.messager.alert('信息提示','提交失败！','info');
					$('#con_add').dialog('close');
				}
			}
		}); 
	};

	/**
	* Name 载入数据
	*/
	$(function(){
		$('#con-datagrit').datagrid({
			url:'courseServlet.do?method=queryCourse',
			rownumbers:true,
			singleSelect:false,
			pageSize:20,           
			pagination:true,
			multiSort:true,
			fitColumns:true,
			fit:true,
			queryParams: formCourseJson(),// 在请求远程数据的时候发送额外的参数。
			rowStyler:function(index,row){    
		        if (row.courseId > 0){    
		            return 'background-color:#fff;'; // 为课程行设置颜色   
		        }    
		    },
			columns:[[
				{ field:'',checkbox:true},
				{ field:'courseId',title:'课程编号',width:100,sortable:true},
				{ field:'courseName',title:'课程名称',width:180,sortable:true},
				{ field:'enable',hidden:'hidden',title:'是否启用',width:100}
			]]
		});
	});
	
	
	/* 搜索方法*/
 	$("#courseSearchBtn").click(function(){
 		//点击搜索
 		$('#con-datagrit').datagrid({ 
 			queryParams: formCourseJson()
 		});   
 	}); 
	
	/*重置方法*/
 	$("#courseResetBtn").click(function(){
 		$("#searchCourseForm").form('clear');
 		// 重新加载数据
 		$('#con-datagrit').datagrid({ 
 				queryParams: formCourseJson()
 			}); 
 	});

    //将获取到的文本框值转为json
    function formCourseJson() {
    	// 获取文本框的值
    	var courseSearchName = $("#courseSearchName").val();
    	// 返回json
        return {"courseSearchName":courseSearchName};
    }
	
	
	
</script>
</body>
</html>

