<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>成绩报表管理</title>
</head>
<body>
<style type="text/css">
#scoreExcel{
	height: 20px;
    width: 56px;
    padding: 0px 2px;
}
#scoreExcel span{
	line-height: 20px;
}
</style>
	<!-- 标题  -->
	<div id="report-toolbar" style="width: 100%">
		<p align="center">
			课程:<input id="report-course" /> 试卷:<input id="report-paper"
				class="easyui-combobox" /> 班级:<input id="report-class"
				class="easyui-combobox" /> <input type="button" id="btnReport-re"
				value="重置" /> <input disabled type="button" id="btnReport" value="预览"
				onclick="view()" />
				<a class="easyui-linkbutton" id="scoreExcel" >导出成绩</a>
		</p>
	</div>
	<div id="report-view" >
		<table id="report-table"></table>
	</div>
	<!-- 画布  -->
	<div style="width: 100%; height: 90%;">
		<div id="report-chart-div"
			style="width: 50%; height: 90%; float: left">
			<canvas id="report-class-chart"></canvas>
		</div>
		<div id="report-question-div"
			style="width: 50%; height: 90%; float: left">
			<canvas id="report-question-chart"></canvas>
		</div>
	</div>

	<!-- easyui-layout -->
	<script type="text/javascript">
	
	// 清空
	$("#btnReport-re").bind('click',function(){
    	$("#report-course").combobox("clear");
		$("#report-class").combobox("clear");
    	$("#report-paper").combobox("clear");
    	refreshChartDiv("report-class-chart","report-chart-div");
    	refreshChartDiv("report-question-chart","report-question-div");
    	$('#btnReport').attr("disabled","disabled");
    	$('#report-view').panel({closed:true});
    	$("#scoreExcel").removeAttr("href");
	});
	
	// 下拉框
    $("#report-course").combobox({
        url: 'teacherServlet.do?method=getCourseJson',
        valueField: 'id',
        textField: 'name',
        onSelect: function (rec) {
        	$("#scoreExcel").removeAttr("href");
        	$("#report-class").combobox("clear");
        	$("#report-qustion").combobox("clear");
            $("#report-paper").combobox({
                url: 'scoreReport.do?method=getPaper&courseId=' + rec.id,
                valueField: 'id',
                textField: 'name',
                onSelect: function (rec) {
                    var courseId = $("#report-course").combobox("getValue");
                    var paperId = rec.id;
                    $("#scoreExcel").removeAttr("href");
                    // 获取班级
                     $("#report-class").combobox({
                   		url:"scoreReport.do?method=getClass&courseId=" + courseId+"&paperId="+rec.id,
                    	valueField:'id',    
                        textField:'name', 
                    	onSelect:function(rec){
                    		
                    		var courseId = $("#report-course").combobox("getValue");
                    		var paperId = $("#report-paper").combobox("getValue");
                    		var classId = rec.id;
                    		showQuestionAll(courseId,paperId,classId);
                    		if(rec.id){
                    			$("#btnReport").removeAttr("disabled");
                    			var url = "scoreReport.do?method=getScoreExcel&courseId=" + courseId+"&paperId="+paperId+"&classId="+classId;
                    			$("#scoreExcel").attr('href',url);
                    		 }
                    		
                        }
                    });
                }
            });
            showMychart(rec.id);
        }
    });
   
 	// 预览班级成绩
    function view(){
		var courseId = $("#report-course").combobox("getValue");
		var classId = $("#report-class").combobox("getValue");
		var paperId = $("#report-paper").combobox("getValue");
		 $('#report-view').panel({
			height:400,
		   	closed:false,
		   	title:'预览成绩',
		   	closable:true,
		   	onOpen:function(){
		   	 $('#report-table').datagrid({
		    	   url:'scoreReport.do?method=getViewScore&courseId='+courseId+"&classId="+classId+"&paperId="+paperId,
			       rownumbers: true,
			       singleSelect: false,
			       pageSize: 10,
			       pagination: true,
			       multiSort: true,
			       fitColumns: true,
			       fit: true,
			       columns: [[
			    	   {field: 'stu_id', title: '学号', width: 100},
			    	   {field: 'stu_real_name', title: '姓名', width: 100},
			    	   {field: 'auto_score', title: '客观分数', width: 100},
			    	   {field: 'sub_score', title: '主观分数', width: 100},
			    	   {field: 'total_score', title: '总分数', width: 100}
			    	   ]]
			 	});
		   	}
		});
	}
   
    // 通过课程查询显示表
    var showMychart = function (courseId, paperId) {
        var courseName = $("#report-course").combobox('getText');
        var paperName = $("#report-paper").combobox('getText');
        var url = "scoreReport.do?method=reportAllClass&courseId=" + courseId ;
        if(paperId){
        	url +="&paperId=" + paperId;
        }
        var ctx = refreshChartDiv("report-class-chart","report-chart-div");
        show(ctx,url,courseName,paperName);
    }
    // 查询所有题目类型对应的概率方法
    var showQuestionAll = function (courseId, paperId,classId) {
        var courseName = $("#report-course").combobox('getText');
        var paperName = $("#report-paper").combobox('getText');
        var className = $("#report-class").combobox('getText');
        var url = "scoreReport.do?method=reportQuestionAll&courseId=" + courseId+
        		"&classId="+classId+"&paperId="+paperId;
        var ctx = refreshChartDiv("report-question-chart","report-question-div");
        show(ctx,url,courseName,paperName,className);
    }
    
    // 显示表的方法
    function show(ctx,url,courseName,paperName,className,typeName){
    	if(!className){
    		className='';
    	}
    	if(!typeName){
    		typeName='';
    	}
    	
    	$.ajax({
            url: url,
            dataType: 'json',
        }).done(function (results) {
            var datasets = [];
            if (results.length > 1) {

                for (var i = 1; i < results.length; i = i + 2) {
                    var jsonData = {};
                    var data = [];
                    for(var j = 0 ; j < results[i + 1].length ; j++){
                    	data.push(results[i + 1][j].toFixed(2));
                    }
                    jsonData.label = results[i];
                    jsonData.data = data;
                    jsonData.borderWidth = 1;
                    jsonData.backgroundColor = [
                        getColor(),
                        getColor(),
                        getColor(),
                        getColor()
                    ];

                    datasets.push(jsonData);
                }
            }
           
            //var ctx = document.getElementById(chartId).getContext("2d");
            var barChartData = {
                labels: results[0],
                datasets: datasets
            };
            // 创表
            var scoreBar = new Chart(ctx, {
                type: 'bar',
                data: barChartData,
                options: {
                    responsive: true,
                    legend: {
                        position: 'top',
                    },
                    title: {
                        display: true,
                        text: courseName+paperName+className+typeName
                    },
                    "scales" : {
            			"yAxes" : [ {
            				"ticks" : {
            					"beginAtZero" : true,
            					"max" : 100,
            				}
            			} ]
            		}
                }
            });
            scoreBar.update();
        });
    	
    }
    
    // 画表
    var refreshChartDiv = function(chartId,divId){
    	 var app = "<canvas id=\""+chartId+"\" style=\"width:100%;height:100%\"></canvas>";
    	
    	 $('#'+chartId).remove();
    	 $('#'+divId).append($(app));
         return document.getElementById(chartId).getContext("2d");
    }
    
    // 颜色
    var getColor = function () {
        var colorNames = Object.keys(window.chartColors);
        var colorName = colorNames[randomScalingFactor() % colorNames.length];
        return window.chartColors[colorName];
    }
</script>
</body>
</html>

