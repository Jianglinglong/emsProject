<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>开始考试</title>
</head>
<body>

<div class="easyui-layout" data-options="fit:true">

    <table id="startexam-datagrid" class="easyui-datagrid" toolbar="#startexam-toolbar"></table>
</div>
<!-- 添加和修改页面 -->
<div id="stu-start-exam"></div>
<!-- End of easyui-dialog -->
<script type="text/javascript">


    /**
     *    查看试卷信息
     */
    function show(paperID) {
        window.open('studentExam.do?method=initStartExam&paperID=' + paperID);

    }

    /**
     * Name 载入数据
     */
    $('#startexam-datagrid').datagrid({
        url: 'startExam.do?method=getPapers',
        rownumbers: true,
        singleSelect: false,
        pageSize: 20,
        pagination: true,
        multiSort: true,
        fitColumns: true,
        fit: true,
        columns: [[
            {
                field: 'courseID', title: '考试科目', width: 110, sortable: true, formatter: function (value, row, index) {
                $.ajaxSettings.async = false;
                var courseName = "";
                $.get('courseServlet.do?method=getCourseNameById', {'courseId': value}, function (data) {
                    courseName = data;
                });
                $.ajaxSettings.async = true;
                return courseName;
            }
            },
            {field: 'examID', title: '考试试卷', width: 100, hidden: true},
            {field: 'paperID', title: '考试试卷', width: 100},
            {
                field: 'teaID', title: '监考老师', width: 100, sortable: true, formatter: function (value, row, index) {
                $.ajaxSettings.async = false;
                var courseName = "";
                $.get('teacherServlet.do?method=getTeacherById', {'teaId': value}, function (data) {
                    courseName = data;
                });
                $.ajaxSettings.async = true;
                return courseName;
            }

            },
            {field: 'examTime', title: '考试时间', width: 100},
            {field: 'locationId', title: '考试地点', width: 100},
            {
                field: 'startExam', title: '操作', align: 'center', width: '10%',
                formatter: function (value, row) {
                    return "<a onclick='show(" + row.examID + ")' > 开始考试 </a>";
                }
            }
        ]]
    });
</script>

</body>
</html>