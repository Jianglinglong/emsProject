<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>学生管理</title>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
	    <div data-options="region:'center',border:false">
	        <table id="pe-datagrit" toolbar="#pe-toolbar"></table>
	    </div>
	</div>
	
<script type="text/javascript">
	/**
	* Name 载入数据
	*/
	$(function(){
		$('#pe-datagrit').datagrid({
			url:'personScoreServlet.do?method=queryPersonScore',
			rownumbers:true,
			singleSelect:false,
			fitColumns:true,
			fit:true,
			rowStyler:function(index,row){    
		        if (row.scoreId > 0){    
		            return 'background-color:#fff;'; // 为成绩行设置颜色   
		        }    
		    },
			columns:[[
				{ field:'scoreId',title:'成绩编号',width:100,sortable:true},
				{ field:'stuId',title:'学生姓名',width:100,sortable:true,formatter:function(value,row,index){
	            	$.ajaxSettings.async =false;
	            	var courseName ="";
	            	$.get('studentServlet.do?method=getStudentNameById',{'stuId':value},function(data){
		            	courseName = data;
	            	});
	            	$.ajaxSettings.async =true;
	            	return courseName;
	            }},
				{ field:'paperId',title:'试卷编号',width:180,sortable:true},
				{ field:'courseId',title:'科目',width:100,formatter:function(value,row,index){
	            	$.ajaxSettings.async =false;
	            	var courseName ="";
	            	$.get('courseServlet.do?method=getCourseNameById',{'courseId':value},function(data){
		            	courseName = data;
	            	});
	            	$.ajaxSettings.async =true;
	            	return courseName;
	            }},
				{ field:'autoScore',title:'客观成绩',width:100},
				{ field:'subScore',title:'主观成绩',width:100},
				{ field:'totalScore',title:'总成绩',width:100}
			]]
		});
	});
	
</script>
</body>
</html>

