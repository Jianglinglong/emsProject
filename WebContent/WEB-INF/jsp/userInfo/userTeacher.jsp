<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/jstl.jspf" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>显示用户信息</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'center',border:false">
	        <table id="teacher-datagrid" toolbar="#pe-toolbar"></table>
	    </div>
	</div>
	<!-- 修改个人密码 start-->
		<div id="tea-update" style="width:400px; padding:10px;">
		    <form id="tea-form" method="post">
		        <table>
		            <tr>
		                <td><input type="hidden" name="teaId" value="${teacher.teaId }"/></td>
		            </tr>
		            <tr>
		                <td width="60" align="right">姓名:</td>
		                <td><input type="text" name="teaRealName" readonly value="${teacher.teaRealName }" style="background-color:gray;" class="easyui-textbox"/></td>
		            </tr>
		            <tr>
		                <td align="right">账号:</td>
		                <td><input type="text" name="teaAccount" readonly value="${teacher.teaAccount }" style="background-color:gray;" class="easyui-textbox"/></td>
		            </tr>
		            <tr>
		                <td align="right">密码:</td>
		                <td><input type="password" name="teaPassword" id="teaPassword" class="easyui-textbox" data-options="required:true"/></td>
		            </tr>
		            <tr>
		                <td valign="top" align="right">备注:</td>
		                <td><input type="text" name="teaRemark" value="${teacher.teaRemark }" class="easyui-textbox"/></td>
		            </tr>
		        </table>
		    </form>
		</div>
	<!-- 修改个人密码 end-->
<script type="text/javascript">
	/**
	* Name 载入数据
	*/
	$(function(){
		$('#teacher-datagrid').datagrid({
			url:'userInfo.do?method=listUserInfo',
			rownumbers:true,
			singleSelect:false,
			fitColumns:true,
			fit:true,
			columns:[[
				{ field:'teaId',title:'教师编号',width: 100,sortable:true},
				{ field:'teaRealName',title:'教师姓名',width: 100,sortable:true},
				{ field:'teaAccount',title:'教师账号',width: 100,sortable:true},
//				{ field:'teaPassword',title:'教师密码',width: 100,},
				{ field:'teaRemark',title:'备注',width: 100,},
				{field: 'operation', title: '操作',width: 100,
	                	formatter: function(value,row,index){
	    				if (row.teaId != 0){
	    					return "<a href='javascript:;' onclick='update("+index+");'>修改信息</a>";
	    				} else {
	    					return value;
	    				}
	    			}
				}
			]]
		});
	});  
	
	// 修改方法
	var update = function (index){
			var rows = $("#teacher-datagrid").datagrid('getRows');// 返回当前页的所有行
			var row = rows[index];// 获取行号为index的数据
			$('#tea-form').form('load', row);
			$('#teaPassword').textbox('clear');
            $('#tea-update').dialog({
                closed: false,
                modal: true,
                title: "修改信息",
                buttons: [{
                    text: '确定',
                    iconCls: 'icon-ok',
                    handler: function () {
                    	addAndUpdate("userInfo.do?method=updateUserInfo");
                    }
                }, {
                    text: '取消',
                    iconCls: 'icon-cancel',
                    handler: function () {
                        $('#tea-update').dialog('close');
                    }
                }]
            });
            
		}
	
	/*
    * 修改方法
    */
    var addAndUpdate = function (url) {
        $('#tea-form').form('submit', {
            url: url,
            success: function (data) {
                if (data == "OK") {
                    $("#teacher-datagrid").datagrid("reload");// 重新加载数据库
                    $.messager.alert('信息提示', '提交成功！', 'info');
                    $('#tea-update').dialog('close');
                }
                else {
                    $.messager.alert('信息提示', '提交失败！', 'info');
                }
            }
        });
    };
	
	
</script>
</body>
</html>
