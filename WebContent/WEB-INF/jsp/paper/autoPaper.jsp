<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
	
</head>
<body>
	<style>
		.elegant-aero {
		margin-left:auto;
		margin-right:auto;
		max-width: 800px;
		background: #eee;
		padding: 15px 20px 15px 20px;
		font: 12px Arial, Helvetica, sans-serif;
		color: #666;
		}
		.elegant-aero h1 {
		font: 24px "Trebuchet MS", Arial, Helvetica, sans-serif;
		padding: 10px 10px 10px 20px;
		display: block;
		background: #eee;
		border-bottom: 2px solid #666;
		margin: -20px -20px 15px;
		}
		.elegant-aero h1>span {
		display: block;
		font-size: 11px;
		}
		.elegant-aero label>span {
		float: left;
		margin-top: 10px;
		color: #5E5E5E;
		}
		.elegant-aero label {
		display: block;
		margin: 0px 0px 5px;
		}
		.elegant-aero label>span {
		float: left;
		width: 20%;
		text-align: right;
		padding-right: 15px;
		margin-top: 8px;
		font-weight: bold;
		}
		.elegant-aero input[type="text"], .elegant-aero input[type="email"], .elegant-aero textarea, .elegant-aero select {
		float: left;
		color: #888;
		width: 25%;
		padding: 0px 0px 0px 5px;
		border: 1px solid #C5E2FF;
		background: #FBFBFB;
		outline: 0;
		-webkit-box-shadow:inset 0px 1px 6px #ECF3F5;
		/*box-shadow: inset 0px 1px 6px #ECF3F5;*/
		font: 200 12px/25px Arial, Helvetica, sans-serif;
		height: 20px;
		line-height:15px;
		margin: 2px 6px 16px 0px;
		}
		.elegant-aero textarea{
		height:80px;
		padding: 5px 0px 0px 5px;
		width: 70%;
		}
		.elegant-aero select {
		appearance:none;
		-webkit-appearance:none;
		-moz-appearance: none;
		text-indent: 0.01px;
		text-overflow: '';
		width: 26%;
		}
		.elegant-aero .button{
		padding: 10px 30px 10px 30px;
		background: #66C1E4;
		border: none;
		color: #FFF;
		box-shadow: 1px 1px 1px #4C6E91;
		-webkit-box-shadow: 1px 1px 1px #4C6E91;
		-moz-box-shadow: 1px 1px 1px #4C6E91;
		text-shadow: 1px 1px 1px #5079A3;
		}
		.elegant-aero .button:hover{
		background: #3EB1DD;
		}
	</style>	

<div class="easyui-panel" style="background:#eee;" id="AutoBox">
		<form id="autoPaper" method="post" class="elegant-aero">
			<h1>自动组卷 </h1>
			<label> 
				<span>选择课程:</span>
				<input id="coursIDAuto" name="coursID" required="required" value="请选择课程"  editable="false">
			</label> 
			<input type="hidden" name="ruleType" value="0">
			<input type="hidden" name="ruleId" id="ruleId">
			<input type="hidden" name="ruleName" id="ruleName1">
			
			<span style="margin-left: 37%;display:inline-block;">---------------------------单选择题---------------------------</span>
			<label> 
				<span>题数:</span> <input type="text" class="num" name="choiceIdsNum"  id="choiceIdsNum1"  onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">

				<span>每题分数:</span> <input type="text" class="score" name="choiceIdsScore"  id="choiceIdsScore1"  onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------多选择题---------------------------</span>
			<label> 
				<span>题数:</span> <input type="text" class="num" name="multipleIdsNum" id="multipleIdsNum1" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
				<span>每题分数:</span> <input type="text" class="score" name="multipleIdsScore" id="multipleIdsScore1" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------填空题---------------------------</span>
			<label> 
				<span>题数:</span> <input type="text" class="num" name="blankIdsNum" id="blankIdsNum1" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
				<span>每题分数:</span> <input type="text" class="score" name="blankIdsScore" id="blankIdsScore1" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------判断题---------------------------</span>
			<label> 
				<span>题数:</span> <input type="text" class="num" name="judgeIdsNum" id="judgeIdsNum1" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
				<span>每题分数:</span> <input type="text" class="score" name="judgeIdsScore" id="judgeIdsScore1" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------主观题---------------------------</span>
			<label> 
				<span>题数:</span> <input type="text" class="num" name="subjectiveIdsNum" id="subjectiveIdsNum1" style="ime-mode:disabled" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
				<span>每题分数:</span> <input type="text" class="score" name="subjectiveIdsScore" id="subjectiveIdsScore1" onkeyup="value=value.replace(/[^\d]/g,'') " ng-pattern="/[^a-zA-Z]/">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">-----------------------------统计-----------------------------</span>
			<label> 
				<span>总题数:</span> <input type="text" id="autoAllNum" readonly="readonly" style="color:red;" value="0">
				<span>总分数:</span> <input type="text" id="autoAllScore" readonly="readonly" style="color:red;" value="0">
			</label> 
			<span>&nbsp;</span> 
			<input type="button" value="提交" id="subbutton1" class="button" onclick="" style="margin-left:43%;">
			&nbsp;&nbsp;&nbsp;
			<a href="javascript:clearAuto();" style="font-size: 14px; color: #999">清除</a>
		</form>

</div>



		<script>
			$(function(){
				$(".score").attr("readonly","readonly");
			});
			var num = 0;
		  	var score = 0;
			// 提交自动组卷
			function paperSubmitAuto() {
				$("#autoPaper").form('submit', {
					url : "paper.do?method=saveAutoPaper",
					onSubmit : function() {
						var course = $("#coursIDAuto").combobox("getValue");
						if(course == "请选择课程") {
							alert("请选择课程！");
							return false;
						}
					},
					success : function(data) {
						if(data == "OK") {
							alert("提交成功！");
							clearAuto();
							$("#autoPaperDialog").dialog('close'); 
							$("#rules-list-datagrid").datagrid("reload");
						} else {
							alert("提交失败！");
						}
					}
				});
			}
			
			// 提交修改内容
			function paperSubmitUpdate1() {
				$("#autoPaper").form('submit', {
					url : "paper.do?method=updateAutoPaper",
					success : function(data) {
						if(data == "OK") {
							alert("修改成功！");
							clearAuto();
							$("#autoPaperDialog").dialog('close'); 
							$("#rules-list-datagrid").datagrid("reload");
						} else {
							alert("修改失败！");
						}
					}
				});
			}

			// 清除文本框内容
			function clearAuto() {
				num = 0;
			  	score = 0;
				$(".num").val("");
				$(".score").val("");
				$(".score").attr("readonly",true);
				$("#autoAllNum").val(0);
				$("#autoAllScore").val(0);
			}
			
			/**
		  	 * 创建课程的下拉框
		  	 */
		  	$('#coursIDAuto').combobox({    
		  	    url:'exam.do?method=getCourseJson',    
		  	    valueField:'id',    
		  	    textField:'name',
		  	  	panelMaxHeight:'100'
		  	});  
			
		  	
			/**
			 * 当输入框失去*获得焦点时计算总数
			 */
			 // 单选
			 $(".num").blur(function(){
				 var index = $(".num").index(this);
				 if($(this).val() == "") {
					 num = num + 0;
					 $(".score").eq(index).val("");
					 $(".score").eq(index).attr("readonly",true);
				 } else if($(".score").eq(index).val() == ""){
					 $(".score").eq(index).attr("readonly",false);
					 num = num + parseInt($(this).val());
				 } else if($(this).val() != "" && $(".score").eq(index).val() != ""){
					 $(".score").eq(index).attr("readonly",false);
					 num = num + parseInt($(this).val());
					 score = score + parseInt($(this).val())*parseInt($(".score").eq(index).val());
				 }
				 $("#autoAllNum").val(num);
				 $("#autoAllScore").val(score);
			 });
			 $(".num").focus(function(){
				 var index = $(".num").index(this);
				 if($(this).val() == "") {
					 num = num - 0;
				 } else if($(".score").eq(index).val() == ""){
					 num = num - parseInt($(this).val());
				 } else if($(".score").eq(index).val() != ""){
					 num = num - parseInt($(this).val());
					 score = score - parseInt($(this).val())*parseInt($(".score").eq(index).val());
				 }
				 $("#autoAllNum").val(num);
				 $("#autoAllScore").val(score);
			 });
			 
			 $(".score").blur(function(){
				 var index =$(".score").index(this);
				 if($(this).val() == "") {
					 score = score + 0;
				 } else {
					 score = score + parseInt($(this).val())*parseInt($(".num").eq(index).val());
				 }
				 $("#autoAllScore").val(score);
			 });
			 $(".score").focus(function(){
				 var index = $(".score").index(this);
				 if($(this).val() == "") {
					 score = score - 0;
				 } else {
					 score = score - parseInt($(this).val())*parseInt($(".num").eq(index).val());
				 }
				 $("#autoAllScore").val(score);
			 });
			 
		</script>
	
</body>
</html>