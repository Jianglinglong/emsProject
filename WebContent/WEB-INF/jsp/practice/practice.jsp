<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/jstl.jspf" %>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
<style type="text/css">
    .item {
        width: 200px;
        height: 200px;
        border-radius: 50%;
        -webkit-box-shadow: inset 0 0 0 10px rgba(200, 200, 200, 0.6), 0 1px 2px rgba(0, 0, 0, 0.1);
        -moz-box-shadow: inset 0 0 0 10px rgba(200, 200, 200, 0.6), 0 1px 2px rgba(0, 0, 0, 0.1);
        box-shadow: inset 0 0 0 10px rgba(200, 200, 200, 0.6), 0 1px 2px rgba(0, 0, 0, 0.1);
        -webkit-transition: all 0.4s ease-in-out;
        -moz-transition: all 0.4s ease-in-out;
        -ms-transition: all 0.4s ease-in-out;
        -o-transition: all 0.4s ease-in-out;
        transition: all 0.4s ease-in-out;
        position: relative;
        text-align: center;
        float: left;
        margin: 20px 0 0 65px;
        cursor: pointer;
    }

    .info {
        background: rgba(29, 160, 40, 0.8);
        width: 200px;
        height: 200px;
        border-radius: 50%;
        opacity: 0;
        -webkit-transition: all 0.4s ease-in-out;
        -moz-transition: all 0.4s ease-in-out;
        -ms-transition: all 0.4s ease-in-out;
        -o-transition: all 0.4s ease-in-out;
        transition: all 0.4s ease-in-out;
        -webkit-transform: scale(0);
        -moz-transform: scale(0);
        -ms-transform: scale(0);
        -o-transform: scale(0);
        transform: scale(0);
        position: absolute;
        top: 0;
        left: 0;
    }

    .item:hover {
        -webkit-box-shadow: inset 0 0 0 1px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.5);
        -moz-box-shadow: inset 0 0 0 1px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.5);
        box-shadow: inset 0 0 0 1px rgba(255, 255, 255, 0.5), 0 1px 2px rgba(0, 0, 0, 0.5);
    }

    .item:hover .info {
        -webkit-transform: scale(1);
        -moz-transform: scale(1);
        -ms-transform: scale(1);
        -o-transform: scale(1);
        transform: scale(1);
        opacity: 1;
    }

    .item:hover label {
        -webkit-transform: scale(0);
        -moz-transform: scale(0);
        -ms-transform: scale(0);
        -o-transform: scale(0);
        transform: scale(0);
        opacity: 0;
    }

    /* 美化一下文字 */

    .info h3 {
        color: #fff;
        font-size: 20px;
        margin: 0 50px;
        padding: 65px 0 0 0;
        font-family: 'microsoft yahei';
        font-weight: normal;
        margin-top: -13px;

    }

    label {
        color: #666;
        font-size: 20px;
        margin: 0 50px;
        padding: 65px 0 0 0;
        font-family: 'microsoft yahei';
        font-weight: normal;
        line-height: 100%;
        display: inline-block;
        margin-top: -13px;
    }
</style>
<div class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'center'">
        <div style="margin-top:20px">
            <div class="item" style="background: #f6f6f6;">
                <label>单项选择题</label>
                <label>练习</label>
                <a class="practice" tp="choice">
                    <div class="info">
                        <h3>单项选择题</h3>
                        <h3>练习</h3>
                    </div>
                </a>
            </div>
            <div class="item" style="background: #f6f6f6;">
                <label>多项选择题</label>
                <label>练习</label>
                <a class="practice" tp="multiple">
                    <div class="info">
                        <h3>多项选择题</h3>
                        <h3>练习</h3>
                    </div>
                </a>
            </div>
            <div class="item" style="background: #f6f6f6;">
                <label>填空题</label>
                <label>练习</label>
                <a class="practice" tp="blank">
                    <div class="info">
                        <h3>填空题</h3>
                        <h3>练习</h3>
                    </div>
                </a>
            </div>
            <div class="item" style="background: #f6f6f6;">
                <label>判断题</label>
                <label>练习</label>
                <a class="practice" tp="judge">
                    <div class="info">
                        <h3>判断题</h3>
                        <h3>练习</h3>
                    </div>
                </a>
            </div>
            <div class="item" style="background: #f6f6f6;">
                <label>主观题</label>
                <label>练习</label>
                <a class="practice" tp="subjective">
                    <div class="info">
                        <h3>主观题</h3>
                        <h3>练习</h3>
                    </div>
                </a>
            </div>
            <!-- <div class="item" style="background: #f6f6f6;">
                <label>错题分析</label>
                <a class="practice">
                    <div class="info">
                        <h3>错题分析</h3>
                    </div>
                </a>
            </div>	 -->
            <div class="item" style="background: #f6f6f6;">
                <label>模拟考试</label>
                <a class="practice">
                    <div class="info">
                        <h3>模拟考试</h3>
                    </div>
                </a>
            </div>
        </div>
    </div>
    <!-- <div id="practice-context" data-options="region:'center',fit:'true'">
    </div> -->
    <div data-options="region:'south'" style="height: 35px">
    </div>
    <div id="choose-practice-dialog" style="width: 300px;height: 193px;padding: 20px" class="easyui-dialog"
         data-options="closed:true">
        <form method="post" id="choose-practice-form">
            <input type="hidden" name="ctype" id="ctype">
            <table>
                <tr>
                    <th>科目</th>
                    <td><input type="text" name="courseId" id="practiceCourseId" required="required" editable="false">
                    </td>
                </tr>
                <tr>
                    <th>数量</th>
                    <td><input type="text" name="practiceNum" id="practiceNum" class="easyui-numberbox"
                               required="required"></td>
                </tr>
            </table>
        </form>
    </div>

    <div id="choose-examination-dialog" style="width: 300px;height: 193px;padding: 20px" class="easyui-dialog"
         data-options="closed:true">
        <form method="post" id="choose-examination-form">
            <table>
                <tr>
                    <th>科目</th>
                    <td><input type="text" name="courseId" id="examinationCourseId" required="required"
                               editable="false"></td>
                </tr>
            </table>
        </form>
    </div>
</div>


<script type="text/javascript">
    $('#practiceCourseId').combobox({
        url: 'courseServlet.do?method=getCourseByUserId',
        valueField: 'id',
        textField: 'name',
        panelMaxHeight: '100'
    });
    $('#examinationCourseId').combobox({
        url: 'courseServlet.do?method=getCourseByUserId',
        valueField: 'id',
        textField: 'name',
        panelMaxHeight: '100'
    });

    $(function () {
        $(".practice").bind('click', function () {
            var index = $('.practice').index(this);
            var titletext = $(this).text();
            var tp = $(this).attr("tp");
            $.messager.confirm("请选择", "是否开始" + titletext, function (r) {
                if (r) {
                    $("#choose-practice-form").form("clear");
                    $("#ctype").attr("value", tp);
                    var url = '${basePath}/practice.do?method=practice&type=' + index;
                    if (index < 5) {
                        $("#choose-practice-dialog").dialog({
                            closed: false,
                            title: titletext,
                            modal: true,
                            buttons: [{
                                text: '确定',
                                iconCls: 'icon-ok',
                                handler: function () {
                                    $("#choose-practice-form").form('submit', {
                                        url: "practice.do?method=getSuject",
                                        success: function (data) {
                                            if (data == "NO") {
                                                $.messager.alert("提示信息", "初始化题目失败!");
                                            } else {
                                                window.open(data);
                                            }
                                        }
                                    });
                                    $("#choose-practice-dialog").dialog('close');
                                }
                            }, {
                                text: '取消',
                                iconCls: 'icon-cancel',
                                handler: function () {
                                    $("#choose-practice-dialog").dialog('close');
                                }
                            }]
                        });
                    } else {
                        $("#choose-examination-dialog").dialog({
                            closed: false,
                            title: "开始模拟测试？",
                            buttons: [{
                                text: '确定',
                                iconCls: 'icon-ok',
                                handler: function () {
                                    var courseId = $("#examinationCourseId").combobox('getValue');
                                    window.open("practice.do?method=initexamination&courseId=" + courseId);
                                    $("#choose-examination-dialog").dialog('close');
                                }
                            }, {
                                text: '取消',
                                iconCls: 'icon-cancel',
                                handler: function () {
                                    $("#choose-examination-dialog").dialog('close');
                                }
                            }]
                        });
                    }
                }
            });
        });
    });
</script>
</body>
</html>
