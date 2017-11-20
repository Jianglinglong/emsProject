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
		box-shadow: inset 0px 1px 6px #ECF3F5;
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
	<div id="handBox" class="easyui-panel" style="background:#eee;">
		<form id="handPaper" method="post" class="elegant-aero">
			<h1>手动组卷 </h1>
			<label> 
				<span>选择课程:</span>
				<input id="coursIDHand" name="coursID" required="required" value="请选择课程" editable="false">
			</label> 
			<input type="hidden" name="ruleType" value="1">
			<input type="hidden" name="ruleId" id="ruleId">
			<input type="hidden" name="ruleName" id="ruleName">
			
			<span style="margin-left: 37%;display:inline-block;">---------------------------单选择题---------------------------</span>
			<label> 
				<span>题号和分数:</span>
                <input type="text" name="choiceIds" readonly
                       style="cursor: pointer;" id="choiceIds" placeholder="点击这里获取试题"
                       onclick="getExam('paper.do?method=getChoice','choiceIds')">
				<span>单选择题总分:</span> <input type="text" class="handScore" name="choiceIdsScore" value="0" id="choiceIdsScore" readonly>
				<input type="hidden" name="choiceIdsNum" class="handNum" value="0" id="choiceIdsNum">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------多选择题---------------------------</span>
			<label> 
				<span>题号和分数:</span> <input type="text" name="multipleId" readonly style="cursor: pointer;" id="multipleIds" placeholder="点击这里获取试题" onclick="getExam('paper.do?method=getMultiple','multipleIds')">
				<span>多选择题总分:</span> <input type="text" class="handScore" name="multipleIdsScore" value="0" id="multipleIdsScore" readonly>
				<input type="hidden" name="multipleIdsNum" class="handNum" value="0" id="multipleIdsNum">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------填空题---------------------------</span>
			<label> 
				<span>题号和分数:</span> <input type="text" name="blankIds" readonly style="cursor: pointer;" id="blankIds" placeholder="点击这里获取试题" onclick="getExam('paper.do?method=getBlank','blankIds')">
				<span>填空题总分:</span> <input type="text" class="handScore" name="blankIdsScore" value="0" id="blankIdsScore" readonly>
				<input type="hidden" name="blankIdsNum" class="handNum" value="0" id="blankIdsNum">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------判断题---------------------------</span>
			<label> 
				<span>题号和分数:</span> <input type="text" name="judgeIds" readonly style="cursor: pointer;" id="judgeIds" placeholder="点击这里获取试题" onclick="getExam('paper.do?method=getJudge','judgeIds')">
				<span>判断题总分:</span> <input type="text" class="handScore" name="judgeIdsScore" value="0" id="judgeIdsScore" readonly>
				<input type="hidden" name="judgeIdsNum" class="handNum" value="0" id="judgeIdsNum">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">---------------------------主观题---------------------------</span>
			<label> 
				<span>题号和分数:</span> <input type="text" name="subjectiveIds" readonly style="cursor: pointer;" id="subjectiveIds" placeholder="点击这里获取试题" onclick="getExam('paper.do?method=getSubjective','subjectiveIds')">
				<span>主观题总分:</span> <input type="text" class="handScore" name="subjectiveIdsScore" value="0" id="subjectiveIdsScore" readonly>
				<input type="hidden" name="subjectiveIdsNum" class="handNum" value="0" id="subjectiveIdsNum">
			</label> 
			<span style="margin-left: 37%;display:inline-block;">-----------------------------统计-----------------------------</span>
			<label> 
				<span>总题数:</span> <input type="text" id="handAllNum" readonly="readonly" style="color:red;" value="0">
				<span>总分数:</span> <input type="text" id="handAllScore" readonly="readonly" style="color:red;" value="0">
			</label> 
			<span>&nbsp;</span> 
			<input type="button" class="button" id="subbutton" value="提交" onclick="" style="margin-left:43%;">
			&nbsp;&nbsp;&nbsp;
	    	<a href="javascript:clear();" style="font-size: 14px;color: #999">清除</a>
		</form>
	</div>
	<div id="promptDiv"   style="width:400px; padding:10px;">
	
	</div>
	
	<script>
		function getExam(url,name){
			var courseId = null;
			courseId = $("#coursIDHand").combobox('getValue');
			$("#promptDiv").dialog({
				title:"题型窗口",
				width:800,
				height:600,
				left:300,
				top:100,
				closed:false,
				modal:true,
				href:url+"&courseId="+courseId,
				onLoad:function(){
					var data =$("#"+name).val();
					if(data != ""){
						var checked = eval('('+data+')');
						for(var i = 0 ; i < checked.length; i++) {
							var id = checked[i].id;
							var score = checked[i].score;
							$("#"+ id).attr('checked',true);
							$("#score"+id).attr('value',score);
						}
					}
				},
				buttons:[{
					text:"确定",
					handler:function() {
						// 获取选择复选框对象
						var $chks = $("#promptDiv input:checkbox:checked");
						// 定义json字符串格式
						var json = "[";
						// 定义总分
						var totalScore = 0;
						// 定义数量
						var num = 0;
						// 遍历所有选择的复选框对象
						$chks.each(function() {
							var $ck = $(this);
							// 获取复选框对象vlaue值
							var value = $ck.val();
							// 获取分数
							var score = $ck.next().val();
							// 计算总分
							totalScore += parseFloat(score);
							json +="{\"id\":"+value+",\"score\":"+score+"},"
							num ++;
						});
						json = json.substring(0,json.length-1);
						if(json.length>1){
							json+="]";
						}
						// 填充内容
						$("#"+name).attr("value",json);
						// 填充总分
						$("#"+name+"Score").attr("value",totalScore);
						// 填充总数
						$("#"+name+"Num").attr("value",num);
						
						var allNum = 0;
						var allScore = 0;
						$(".handNum").each(function(){
							allNum += parseInt($(this).val());
						});
						$(".handScore").each(function(){
							allScore += parseInt($(this).val());
						});
						$("#handAllNum").attr("value",allNum);
						$("#handAllScore").attr("value",allScore);
						// 关闭模态 
						$('#promptDiv').dialog('close'); 
						
					}
				}]
			});
	}
	
	
	// 清除文本框内容
	function clear(){
	 	$("#choiceIds").attr("value","");
		$("#choiceIdsScore").attr("value",0);
		$("#choiceIdsNum").attr("value",0);
		$("#multipleIds").attr("value","");
		$("#multipleIdsScore").attr("value",0);
		$("#multipleIdsNum").attr("value",0);
		$("#blankIds").attr("value","");
		$("#blankIdsScore").attr("value",0);
		$("#blankIdsNum").attr("value",0);
		$("#judgeIds").attr("value","");
		$("#judgeIdsScore").attr("value",0);
		$("#judgeIdsNum").attr("value",0);
		$("#subjectiveIds").attr("value","");
		$("#subjectiveIdsScore").attr("value",0);
		$("#subjectiveIdsNum").attr("value",0);
		$("#handAllNum").attr("value",0);
		$("#handAllScore").attr("value",0); 
	}
	
	function paperSubmit(){
		$("#handPaper").form('submit',{
			url:"paper.do?method=saveHandPaper",
			onSubmit:function(){
				var course = $("#coursIDHand").combobox("getValue");
				if(course == "请选择课程") {
					alert("请选择课程！");
					return false;
				}
			},
			success:function(data){
				if(data == "OK") {
					alert("提交成功！");
					clear();
					$("#handPaperDialog").dialog('close'); 
					$("#rules-list-datagrid").datagrid("reload");
				} else {
					alert("提交失败！");
				}
			}
		});
	}
	
	function paperSubmitUpdate(){
		$("#handPaper").form('submit',{
			url:"paper.do?method=updateHandRule",
			onSubmit:function(){
				
			},
			success:function(data){
				if(data == "OK") {
					alert("修改成功！");
					clear();
					$("#handPaperDialog").dialog('close'); 
					$("#rules-list-datagrid").datagrid("reload");
				} else {
					alert("修改失败！");
				}
			}
		});
	}
	
	/**
  	 * 创建课程的下拉框
  	 */
  	 
  	$('#coursIDHand').combobox({    
  	    url:'exam.do?method=getCourseJson',    
  	    valueField:'id',    
  	    textField:'name',
  	  	panelMaxHeight:'100'
  	}); 
	</script>
	
		
</body>
</html>