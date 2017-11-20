<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        <div id="rules-list-toolbar">
            <div class="wu-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addListRulesByHand()" plain="true">手动添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="addListRulesByAuto()" plain="true">自动添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="updateListRules()" plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeListRules()" plain="true">删除</a>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="rules-list-datagrid" toolbar="#rules-list-toolbar"></table>
        
        <div id="handPaperDialog"></div>
        <div id="autoPaperDialog"></div>
    </div>
</div>
<script type="text/javascript">
 
    /**
     * Name 删除记录
     */
    function removeListRules() {
        var items = $('#rules-list-datagrid').datagrid('getSelections');
        if (items.length != 0) {
            $.messager.confirm('信息提示', '确定要删除该记录？', function (result) {
                if (result) {
                    var ids = [];
                    $(items).each(function () {
                        ids.push(this.ruleID)
                    });
                    var url = 'paper.do?method=deletePaperRule';
                    $.get(url, {stuId: ids.toString()}, function (data) {
                        if (data == "OK") {
                            $.messager.alert('信息提示', '删除成功！', 'info');
                            $('#listRules_add').dialog('close');
                        } else if (data == "NO") {
                            $.messager.alert('信息提示', '删除部分！', 'info');

                            $('#listRules_add').dialog('close');
                        }
                        $("#rules-list-datagrid").datagrid("reload");// 重新加载数据库
                    });
                }
            });
        }
    }


    /**
     * 手动添加试卷
     */
     function addListRulesByHand() {
    	$("#handPaperDialog").dialog({
    		width:800,
    		height:600,
    		top:100,
    		title:'手动添加试卷',
    		href:"paper.do?method=initHandPaper",
    		modal:true,
    		onLoad:function(){
    			clear();
    			$("#subbutton").attr("onclick","paperSubmit()");
    		},
    		onBeforeClose:function(){
    			$('#handBox').remove();
    		}
    	});
     }
    
    /**
     * 自动添加试卷
     */
     function addListRulesByAuto() {
     	$("#autoPaperDialog").dialog({
     		width:800,
     		height:600,
     		top:100,
     		title:'自动添加试卷规则',
     		href:"paper.do?method=initAutoPaper",
     		modal:true,
     		onLoad:function(){
     			clearAuto();
    			$("#subbutton1").attr("onclick","paperSubmitAuto()");
    		},
     		onBeforeClose:function(){
     			$('#AutoBox').remove();
     		}
     	});
      }
    
    /**
     * 修改规则
     */
     function updateListRules(){
    	 var rows = $('#rules-list-datagrid').datagrid('getSelections');
         if (rows.length > 1) {
             $.messager.alert("提示信息", "只能选择一行!");
         } else if(rows.length > 0 ){
        	 if(rows[0].ruleType == 1){
        		 $("#handPaperDialog").dialog({
       	     		width:800,
       	     		height:600,
       	     		top:100,
       	     		title:'修改规则',
       	     		href:"paper.do?method=initHandPaper",
       	     		modal:true,
       	     		onLoad:function(){
       	     			clear();
       	     			$("#subbutton").attr("onclick","paperSubmitUpdate()");
	       	     		$.ajaxSettings.async =false;
	                	var ruleJson ="";
	                	$.get('paper.do?method=getPaperRuleJsonByRuleId',{'ruleId':rows[0].ruleID},function(data){
	                		ruleJson = data;
	                	});
	                	$.ajaxSettings.async =true;
	                	if(ruleJson != "") {
	                		var rule = eval('('+ruleJson+')');
	                		$("#coursIDHand").combobox('select', rule.courseID);
	                		$("#coursIDHand").combobox('readonly', true);
	                		$("#choiceIds").attr("value",rule.singleChoiceChecked);
	                		$("#choiceIdsScore").attr("value",rule.singleScore);
	                		$("#choiceIdsNum").attr("value",rule.singleChoiceNum);
	                		$("#multipleIds").attr("value",rule.mulChoiceChecked);
	                		$("#multipleIdsScore").attr("value",rule.mulScore);
	                		$("#multipleIdsNum").attr("value",rule.mulChoiceNum);
	                		$("#blankIds").attr("value",rule.fileBlankChoiceChecked);
	                		$("#blankIdsScore").attr("value",rule.fileBlankScore);
	                		$("#blankIdsNum").attr("value",rule.fileBlankChoiceNum);
	                		$("#judgeIds").attr("value",rule.judgeChecked);
	                		$("#judgeIdsScore").attr("value",rule.judgeScore);
	                		$("#judgeIdsNum").attr("value",rule.judgeNum);
	                		$("#subjectiveIds").attr("value",rule.subChoiceChecked);
	                		$("#subjectiveIdsScore").attr("value",rule.subScore);
	                		$("#subjectiveIdsNum").attr("value",rule.subChoiceNum);
	                		$("#handAllNum").attr("value",rule.singleChoiceNum+rule.mulChoiceNum+
	                				 rule.fileBlankChoiceNum+rule.judgeNum+rule.subChoiceNum);	
	                		$("#handAllScore").attr("value",rule.paperScore);
	                		$("#ruleId").attr("value",rows[0].ruleID);
	                		$("#ruleName").attr("value",rule.ruleName);
	                	}
	                	
       	     		},
       	     		onBeforeClose:function(){
       	     			$("#handBox").remove();
       	     		}
       	     	});
	            $('#paper-list-form').form('load',rows[0]);
        	 } else if(rows[0].ruleType == 0) {
        		 // 修改自动组卷
        		 $("#autoPaperDialog").dialog({
        	     		width:800,
        	     		height:600,
        	     		top:100,
        	     		title:'修改规则',
        	     		href:"paper.do?method=initAutoPaper",
        	     		modal:true,
        	     		onLoad:function(){
        	     			clearAuto();
        	     			$("#subbutton1").attr("onclick","paperSubmitUpdate1()");
	 	       	     		$.ajaxSettings.async =false;
	 	                	var ruleJson ="";
	 	                	$.get('paper.do?method=getPaperRuleJsonByRuleId',{'ruleId':rows[0].ruleID},function(data){
	 	                		ruleJson = data;
	 	                	});
	 	                	$.ajaxSettings.async =true;
	 	                	if(ruleJson != "") {
	 	                		var rule = eval('('+ruleJson+')');
	 	                		$("#coursIDAuto").combobox('select', rule.courseID);
	 	                		$("#coursIDAuto").combobox('readonly', true);
	 	                		$("#choiceIdsNum1").val(rule.singleChoiceNum);
	 	                		$("#choiceIdsScore1").val(rule.singleScore);
	 	                		$("#multipleIdsNum1").val(rule.mulChoiceNum);
	 	                		$("#multipleIdsScore1").val(rule.mulScore);
	 	                		$("#blankIdsNum1").val(rule.fileBlankChoiceNum);
	 	                		$("#blankIdsScore1").val(rule.fileBlankScore);
	 	                		$("#judgeIdsNum1").val(rule.judgeNum);
	 	                		$("#judgeIdsScore1").val(rule.judgeScore);
	 	                		$("#subjectiveIdsNum1").val(rule.subChoiceNum);
	 	                		$("#subjectiveIdsScore1").val(rule.subScore);
	 	                		$("#autoAllNum").val(rule.singleChoiceNum+rule.mulChoiceNum+
		                				 rule.fileBlankChoiceNum+rule.judgeNum+rule.subChoiceNum);
	 	                		$("#autoAllScore").val(rule.paperScore);
	 	                		$("#ruleId").attr("value",rows[0].ruleID);
	 	                		$("#ruleName1").attr("value",rule.ruleName);
	 	                		num = rule.singleChoiceNum+rule.mulChoiceNum+rule.fileBlankChoiceNum+rule.judgeNum+rule.subChoiceNum;
	 	                		score = rule.paperScore;
	 	                		$(".score").attr("readonly",false);
		 	                }
        	     		},
        	     		onBeforeClose:function(){
        	     			$("#AutoBox").remove();
        	     		}
        	     	});
 	            $('#paper-list-form').form('load',rows[0]);
        		 
        	 }
         } else {
         	$.messager.alert('信息提示', '请选择修改对象！');
         }
    }

    /**
     * Name 载入数据
     */
    $('#rules-list-datagrid').datagrid({
        url: 'paper.do?method=showPaperRule',
        rownumbers: true,
        singleSelect: false,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        fit: true,
        columns: [[
            {field:'',checkbox: true},
            {field: 'ruleID', title: '编号', width: 50, sortable: true},
            {field: 'courseID', title: '课程名', width: 50, sortable: true,formatter:function(value,row,index){
            	$.ajaxSettings.async =false;
            	var courseName ="";
            	$.get('courseServlet.do?method=getCourseNameById',{'courseId':value},function(data){
	            	courseName = data;
            	});
            	$.ajaxSettings.async =true;
            	return courseName;
            }},
            {field: 'ruleName', title: '规则名', width: 110, sortable: true},
            {field: 'singleChoiceNum', title: '单选题数量', width: 100, sortable: true},
            {field: 'mulChoiceNum', title: '多选题数量', width: 100},
            {field: 'fileBlankChoiceNum', title: '填空题数量', width: 100},
            {field: 'judgeNum', title: '判断题数量', width: 100},
            {field: 'subChoiceNum', title: '主观题数量', width: 100},
            {field: 'paperScore', title: '总分', width: 100},
            {field: 'ruleType', title: '组卷类型', width: 100,formatter:function(value,row,index){
            	if(value == 0){
            		return "自动组卷";
            	} else if(value == 1) {
            		return "手动组卷";
            	}
            }}
        ]]
    });
</script>
</body>
</html>