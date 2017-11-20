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
        <div id="stuCourse-toolbar">
            <div class="stuCourse-toolbar-button">
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-add" onclick="openAddstudentCourse()" plain="true">添加</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEditstudentCourse()" plain="true">修改</a>
                <a href="javascript:;" class="easyui-linkbutton" iconCls="icon-remove" onclick="removestudentCourse()" plain="true">删除</a>
                <form style="display: inline-block;" method="post" id="studentCourseForm">
           		课程名称: <input class="easyui-textbox" id="studentCourseName"/>
           		<a class="easyui-linkbutton" id="submitStudentBtn">搜索</a>
           		<a class="easyui-linkbutton" id="resetStudentBtn">重置</a>
           		</form>
            </div>
        </div>
        <!-- End of toolbar -->
        <table id="student-course-datagrid" toolbar="#stuCourse-toolbar"></table>
    </div>
</div>
<!-- Begin of easyui-dialog -->

<!-- 添加和修改页面div-start -->
<div id="student-course-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
    <form id="student-course-form" method="post">
        <table>
            <tr>
                <td><input type="hidden" name="stuCourseId"/></td>
            </tr>
            <tr>
                <td width="60" align="right">课程名称:</td>
                <td><input id="student-course-combobox" type="text" name="courseId" required="required" editable="false"/></td>
            </tr>
            <tr>
                <td width="60" align="right">学生姓名:</td>
                <td><input id="student-name-combobox" type="text" name="stuId" required="required" editable="false"/></td>
            </tr>
        </table>
    </form>
</div>
<!-- 添加 和修改页面div-end -->

<script type="text/javascript">

    /**
     * Name 删除记录
     */
    function removestudentCourse(){
        var items = $('#student-course-datagrid').datagrid('getSelections');
        if(items.length != 0){
            $.messager.confirm('信息提示','确定要删除该记录？', function(result){
                if(result){
                    var ids = [];
                    $(items).each(function(){
                        ids.push(this.stuCourseId);
                    });
                    var url = "studentServlet.do?method=deleteStudentCourse";
                    $.get(url,{stuCourseId:ids.toString()},function(data){
                        if(data=="OK"){
                            $.messager.alert('信息提示','删除成功！','info');
                            $("#student-course-datagrid").datagrid("reload");// 重新加载数据库
                            $('#student-course-dialog').dialog('close');
                        }else if(data == "NO"){
                            $.messager.alert('信息提示','删除部分！','info');
                            $("#student-course-datagrid").datagrid("reload");// 重新加载数据库
                            $('#student-course-dialog').dialog('close');
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
    function openAddstudentCourse(){
        $('#student-course-form').form('clear');
        $('#student-course-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                    updateStuCourse('studentServlet.do?method=addStudentCourse');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#student-course-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEditstudentCourse(){
        var rows = $('#student-course-datagrid').datagrid('getSelections');
        if(rows.length > 1){
            $.messager.alert("提示信息","只能选择一行","info");
        }else{
            var row = $('#student-course-datagrid').datagrid('getSelected');
            if (row != null){
                $('#student-course-dialog').dialog({
                    closed: false,
                    modal:true,
                    title: "修改信息",
                    buttons: [{
                        text: '确定',
                        iconCls: 'icon-ok',
                        handler: function(){
                            updateStuCourse('studentServlet.do?method=updateStudentCourse');
                        }
                    }, {
                        text: '取消',
                        iconCls: 'icon-cancel',
                        handler: function () {
                            $('#student-course-dialog').dialog('close');
                        }
                    }]
                });
                $('#student-course-form').form('load',row);
            }else{
                $.messager.alert("信息提示","请选择修改的数据","info");
            }
        }
    }

    /*
    * 添加和修改方法
    */
    var updateStuCourse = function(url){
        $('#student-course-form').form('submit', {
            url:url,
            success:function(data){
                if(data=="OK"){
                    $.messager.alert('信息提示','提交成功！','info');
                    $("#student-course-datagrid").datagrid("reload");// 重新加载数据库
                    $('#student-course-dialog').dialog('close');
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
        $('#student-course-datagrid').datagrid({
            url:'studentServlet.do?method=showstudentScoure',
            rownumbers:true,
            singleSelect:false,
            pageSize:20,
            pagination:true,
            multiSort:true,
            fitColumns:true,
            fit:true,
            queryParams: formStudentCourseJson(),// 在请求远程数据的时候发送额外的参数。
            rowStyler:function(index,row){    
    	        if (row.stuCourseId > 0){    
    	            return 'background-color:#fff;'; // 为学生课程行设置颜色   
    	        }    
    	    },
            columns:[[
                { field:'',checkbox:true},
                { field:'stuCourseId',title:'学生课程关系编号',width:100,hidden:true},
                { field:'courseId',title:'课程编号',width:180,hidden:true},
                { field:'courseName',title:'课程名称',width:100,},
                { field:'stuName',title:'学生名称',width:100,},
                { field:'stuCourseId',title:'学生编号',width:100,hidden:true},
            ]]
        });
    });

    $('#student-course-combobox').combobox({
        url:'studentServlet.do?method=getCourseJson',
        valueField:'id',
        textField:'name',
        panelMaxHeight:'100',
        onSelect:function(rec){
        }
    })
    ;$('#student-name-combobox').combobox({
        url:'studentServlet.do?method=getStudentJson',
        valueField:'id',
        textField:'name',
        panelMaxHeight:'100',
        onSelect:function(rec){
        }
    });
//    student-name-combobox


	/* 搜索方法*/
 	$("#submitStudentBtn").click(function(){
 		//点击搜索
 		$('#student-course-datagrid').datagrid({ 
 			queryParams: formStudentCourseJson()
 		});   
 	}); 
	
	/*重置方法*/
 	$("#resetStudentBtn").click(function(){
 		$("#studentCourseForm").form('clear');
 		// 重新加载数据
 		$('#student-course-datagrid').datagrid({ 
 				queryParams: formStudentCourseJson()
 		}); 
 	});

    //将表单数据转为json
    function formStudentCourseJson() {
    	var studentCourseName = $("#studentCourseName").val();
    	// 返回json
        return {"studentCourseName":studentCourseName};
    }


</script>
</body>
</html>