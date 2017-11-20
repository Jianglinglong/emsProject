<%--
  Created by IntelliJ IDEA.
  User: Direct
  Date: 2017/10/17
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html>

<head>
    <%@include file="/common/easyui.jspf" %>

</head>
<body>
<style>
	.messager-body :nth-child(2){
	    margin: 27px auto;
	    font-size: 16px;
	    font-family: "Microsoft Yahei";
	}
	.messager-icon{
	    margin-top: 10px;
	    margin-left: 20px;
	}
</style>
<c:if test="${paper==true}">
<div class="main">
    <!--nr start-->
    <div class="test_main">
        <div class="nr_left">
            <div class="test">
                <h1 style="text-align: center">欢迎${sessionScope.stuName }参加${course.courseName}考试</h1>

                <form id="stu-exam-form" method="post">
                    <input type="hidden" name="paperID" value="${paperID}"/>
                    <input type="hidden" name="courseID" value="${course.courseId}">
                    <div class="test_title">
                        <p class="test_time">
                            <b class="timer"></b>
                        </p>
                        <font><input onclick="stu_submit_exam();" type="button" name="test_jiaojuan" value="交卷"></font>
                    </div>
                    <div class="test_content">
                        <div class="test_content_title">
                            <h2>单选题</h2>
                            <p>
                                <span>共</span><i
                                    class="content_lit">${singleChoiceNum }</i><span>题，</span><span>合计</span><i
                                    class="content_fs">${singleScore }</i><span>分</span>
                            </p>

                        </div>
                    </div>
                    <div class="test_content_nr">
                        <ul>
                            <c:set value="1" var="choiceNum"/>
                            <c:forEach items="${choices }" var="choices">
                                <li id="qu_0_${choiceNum }">
                                    <div class="test_content_nr_tt">
                                        <c:forEach items="${choiceQues }" var="choiceQue">
                                            <c:if test="${choiceQue.id eq choices.choiceId}">
                                                <i>${choiceNum }</i><span>(${choiceQue.score }分)</span><font>${choices.choiceTitle }</font>
                                                <input type="hidden" name="singleScore${choices.choiceId}"
                                                       value="${choiceQue.score }"/>
                                                <input type="hidden" name="single" value="${choices.choiceId}"/>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="test_content_nr_main">
                                        <ul>
                                            <li class="option">
                                                <input type="radio" class="radioOrCheck" value="A"
                                                       name="singleChoice${choices.choiceId }"
                                                       id="0_answer_${choiceNum }_option_1"/>
                                                <label for="0_answer_${choiceNum }_option_1">
                                                    A.<p class="ue" style="display: inline;">${choices.answera }</p>
                                                </label>
                                            </li>

                                            <li class="option">
                                                <input type="radio" class="radioOrCheck" value="B"
                                                       name="singleChoice${choices.choiceId }"
                                                       id="0_answer_${choiceNum }_option_2"/>
                                                <label for="0_answer_${choiceNum }_option_2">
                                                    B.<p class="ue" style="display: inline;">${choices.answerb }</p>
                                                </label>
                                            </li>

                                            <li class="option">
                                                <input type="radio" class="radioOrCheck" value="C"
                                                       name="singleChoice${choices.choiceId }"
                                                       id="0_answer_${choiceNum }_option_3"/>
                                                <label for="0_answer_${choiceNum }_option_3">
                                                    C.<p class="ue" style="display: inline;">${choices.answerc }</p>
                                                </label>
                                            </li>

                                            <li class="option">
                                                <input type="radio" class="radioOrCheck" value="D"
                                                       name="singleChoice${choices.choiceId }"
                                                       id="0_answer_${choiceNum }_option_4"/>
                                                <label for="0_answer_${choiceNum }_option_4">
                                                    D.<p class="ue" style="display: inline;">${choices.answerd }</p>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <c:set value="${choiceNum+1 }" var="choiceNum"/>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="test_content">
                        <div class="test_content_title">
                            <h2>多选题</h2>
                            <p>
                                <span>共</span><i
                                    class="content_lit">${mulChoiceNum }</i><span>题，</span><span>合计</span><i
                                    class="content_fs">${mulScore }</i><span>分</span>
                            </p>
                        </div>
                    </div>
                    <div class="test_content_nr">
                        <ul>
                            <c:set value="1" var="mulNum"/>
                            <c:forEach items="${mulChoices }" var="mulChoices">
                                <li id="qu_1_${mulNum }">
                                    <input type="hidden" name="multi" value="${mulChoices.choiceId}"/>
                                    <div class="test_content_nr_tt">
                                        <c:forEach items="${mulChoiceQues }" var="mulChoiceQues">
                                            <c:if test="${mulChoiceQues.id == mulChoices.choiceId }">
                                                <input type="hidden" name="multiScore${mulChoices.choiceId}"
                                                       value="${mulChoiceQues.score }"/>
                                                <i>${mulNum }</i><span>(${mulChoiceQues.score }分)</span><font>${mulChoices.choiceTitle }</font>
                                            </c:if>
                                        </c:forEach>
                                    </div>

                                    <div class="test_content_nr_main">
                                        <ul>
                                            <li class="option">
                                                <input type="checkbox" class="radioOrCheck" value="A"
                                                       name="multChoice${mulChoices.choiceId }"
                                                       id="1_answer_${mulNum }_option_1"/>
                                                <label for="1_answer_${mulNum }_option_1">
                                                    A.<p class="ue"
                                                         style="display: inline;">${mulChoices.answera }</p>
                                                </label>
                                            </li>
                                            <li class="option">
                                                <input type="checkbox" class="radioOrCheck" value="B"
                                                       name="multChoice${mulChoices.choiceId }"
                                                       id="1_answer_${mulNum }_option_2"/>
                                                <label for="1_answer_${mulNum }_option_2">
                                                    B.<p class="ue"
                                                         style="display: inline;">${mulChoices.answerb }</p>
                                                </label>
                                            </li>
                                            <li class="option">
                                                <input type="checkbox" class="radioOrCheck" value="C"
                                                       name="multChoice${mulChoices.choiceId }"
                                                       id="1_answer_${mulNum }_option_3"/>
                                                <label for="1_answer_${mulNum }_option_3">
                                                    C.<p class="ue"
                                                         style="display: inline;">${mulChoices.answerc }</p>
                                                </label>
                                            </li>
                                            <li class="option">
                                                <input type="checkbox" class="radioOrCheck" value="D"
                                                       name="multChoice${mulChoices.choiceId }"
                                                       id="1_answer_${mulNum }_option_4"/>
                                                <label for="1_answer_${mulNum }_option_4">
                                                    D.<p class="ue"
                                                         style="display: inline;">${mulChoices.answerd }</p>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <c:set value="${mulNum+1 }" var="mulNum"/>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="test_content">
                        <div class="test_content_title">
                            <h2>判断题</h2>
                            <p>
                                <span>共</span><i
                                    class="content_lit">${judgeChoiceNum }</i><span>题，</span><span>合计</span><i
                                    class="content_fs">${judgeScore }</i><span>分</span>
                            </p>
                        </div>
                    </div>
                    <div class="test_content_nr">
                        <ul>
                            <c:set value="1" var="judgeNum"/>
                            <c:forEach items="${judges }" var="judges">
                                <li id="qu_2_${judgeNum }">
                                    <input type="hidden" name="judge" value="${judges.judgeId}"/>
                                    <div class="test_content_nr_tt">
                                        <c:forEach items="${judgeQues }" var="judgeQues">
                                            <c:if test="${judgeQues.id == judges.judgeId }">
                                                <input type="hidden" name="judgeScore${judges.judgeId}"
                                                       value="${judgeQues.score }"/>
                                                <i>${judgeNum }</i><span>(${judgeQues.score }分)</span><font>${judges.judgeTitle }</font>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="test_content_nr_main">
                                        <ul>
                                            <li class="option">
                                                <input type="radio" class="radioOrCheck"
                                                       name="judgeNum${judges.judgeId }"
                                                       id="2_answer_${judgeNum }_option_1" value="1"/>
                                                <label for="2_answer_${judgeNum }_option_1">
                                                    <p class="ue" style="display: inline;">正确</p>
                                                </label>
                                            </li>
                                            <li class="option">
                                                <input type="radio" class="radioOrCheck"
                                                       name="judgeNum${judges.judgeId }"
                                                       id="2_answer_${judgeNum }_option_2" value="0"/>
                                                <label for="2_answer_${judgeNum }_option_2">
                                                    <p class="ue" style="display: inline;">错误</p>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <c:set value="${judgeNum+1 }" var="judgeNum"/>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="test_content">
                        <div class="test_content_title">
                            <h2>填空题</h2>
                            <p>
                                <span>共</span><i
                                    class="content_lit">${blankChoiceNum }</i><span>题，</span><span>合计</span><i
                                    class="content_fs">${blankScore }</i><span>分</span>
                            </p>
                        </div>
                    </div>
                    <div class="test_content_nr">
                        <ul>
                            <c:set value="1" var="blankNum"/>
                            <c:forEach items="${blanks }" var="blanks">
                                <li id="qu_3_${blankNum }">
                                    <input type="hidden" name="blank" value="${blanks.blankId}"/>
                                    <div class="test_content_nr_tt">
                                        <c:forEach items="${blankQues}" var="blankQues">
                                            <c:if test="${blankQues.id == blanks.blankId}">
                                                <input type="hidden" name="blankScore${blanks.blankId}"
                                                       value="${blankQues.score }"/>
                                                <i>${blankNum }</i><span>(${blankQues.score }分)</span><font>${blanks.blankTitle }</font>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="test_content_nr_main">
                                        <ul>
                                            <li class="option" style="padding-bottom:10px">
                                                <label for="3_answer_${blankNum }_option_1">
                                                    <input type="text" name="blankNum${blanks.blankId }"
                                                           id="3_answer_${blankNum }_option_1"
                                                           style="width:100%;height:22px;float:none;"/>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <c:set value="${blankNum+1 }" var="blankNum"/>
                            </c:forEach>
                        </ul>
                    </div>

                    <div class="test_content">
                        <div class="test_content_title">
                            <h2>主观题</h2>
                            <p>
                                <span>共</span><i class="content_lit">${subChoiceNum }</i><span>题，</span><span>合计</span><i
                                    class="content_fs">${subScore }</i><span>分</span>
                            </p>
                        </div>
                    </div>
                    <div class="test_content_nr">
                        <ul>
                            <c:set value="1" var="subjectiveNum"/>
                            <c:forEach items="${subjectives }" var="subjectives">
                                <li id="qu_4_${subjectiveNum }">
                                    <input type="hidden" name="subjective" value="${subjectives.subId}"/>
                                    <div class="test_content_nr_tt">
                                        <c:forEach items="${subQues }" var="subQues">
                                            <c:if test="${subQues.id == subjectives.subId}">
                                                <input type="hidden" name="subjectiveScore${subjectives.subId}"
                                                       value="${subQues.score }"/>
                                                <i>${subjectiveNum }</i><span>(${subQues.score }分)</span><font>${subjectives.subTitle }</font>
                                            </c:if>
                                        </c:forEach>
                                    </div>
                                    <div class="test_content_nr_main">
                                        <ul>
                                            <li class="option">
                                                <label for="4_answer_${subjectiveNum }_option_1">
                                                        <textarea  name="subjectiveNum${subjectives.subId}"
                                                                  style="width:100%;height:80px;resize: none;"></textarea>
                                                </label>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <c:set value="${subjectiveNum+1 }" var="subjectiveNum"/>
                            </c:forEach>
                        </ul>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="nr_right">
        <div class="nr_rt_main">
            <div class="rt_nr1">
                <div class="rt_nr1_title">
                    <h1>
                        答题卡
                    </h1>
                    <p class="test_time">
                        <b class="timer"></b>
                    </p>
                </div>
                <div class="rt_content">
                    <div class="rt_content_tt">
                        <h2>单选题</h2>
                        <p>
                            <span>共</span><i class="content_lit">${singleChoiceNum }</i><span>题</span>
                        </p>
                    </div>
                    <div class="rt_content_nr answerSheet">
                        <c:set value="1" var="chNum"/>
                        <ul>
                            <c:forEach items="${choices }" var="choices">
                                <li><a href="#qu_0_${chNum }">${chNum }</a></li>
                                <c:set value="${chNum+1 }" var="chNum"/>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <div class="rt_content">
                    <div class="rt_content_tt">
                        <h2>多选题</h2>
                        <p>
                            <span>共</span><i class="content_lit">${mulChoiceNum }</i><span>题</span>
                        </p>
                    </div>
                    <div class="rt_content_nr answerSheet">
                        <ul>
                            <c:set value="1" var="mNum"/>
                            <c:forEach items="${mulChoices }" var="mulChoices">
                                <li><a href="#qu_1_${mNum }">${mNum }</a></li>
                                <c:set value="${mNum+1 }" var="mNum"/>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <div class="rt_content">
                    <div class="rt_content_tt">
                        <h2>判断题</h2>
                        <p>
                            <span>共</span><i class="content_lit">${judgeChoiceNum }</i><span>题</span>
                        </p>
                    </div>
                    <div class="rt_content_nr answerSheet">
                        <ul>
                            <c:set value="1" var="juNum"/>
                            <c:forEach items="${judges }" var="judges">
                                <li><a href="#qu_2_${juNum }">${juNum }</a></li>
                                <c:set value="${juNum+1 }" var="juNum"/>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <div class="rt_content">
                    <div class="rt_content_tt">
                        <h2>填空题</h2>
                        <p>
                            <span>共</span><i class="content_lit">${blankChoiceNum }</i><span>题</span>
                        </p>
                    </div>
                    <div class="rt_content_nr answerSheet">
                        <ul>
                            <c:set value="1" var="blNum"/>
                            <c:forEach items="${blanks }" var="blanks">
                                <li><a href="#qu_3_${blNum }">${blNum }</a></li>
                                <c:set value="${blNum+1 }" var="blNum"/>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <div class="rt_content">
                    <div class="rt_content_tt">
                        <h2>主观题</h2>
                        <p>
                            <span>共</span><i class="content_lit">${subChoiceNum }</i><span>题</span>
                        </p>
                    </div>
                    <div class="rt_content_nr answerSheet">
                        <ul>
                            <c:set value="1" var="subNum"/>
                            <c:forEach items="${subjectives }" var="subjectives">
                                <li><a href="#qu_4_${subNum }">${subNum }</a></li>
                                <c:set value="${subNum+1 }" var="subNum"/>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--nr end-->
<div class="foot"></div>
</div>
</c:if>
<c:if test="${paper==false}">
    <script type="text/javascript">
        $(function () {
            alert("你已经考过这门考试");
            window.opener = null;
            window.open('', '_self');
            window.close();
            exam_window_close();
        })
    </script>
</c:if>
<link href="${basePath}/css/main.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/css/test.css" rel="stylesheet" type="text/css"/>
<style>
    .hasBeenAnswer {
        background: #5d9cec;
        color: #fff;
    }
</style>
<script src="${basePath}/js/jquery.easy-pie-chart.js"></script>
<!--时间js-->
<script src="${basePath}/js/jquery.countdown.js"></script>
<script>
    var examTime = ${times};
    var time = examTime * 60;
    function timer() {
        // 计算小时
        var hour = handlerTime(Math.floor(time / 3600));
        // 计算分钟
        var minutes = handlerTime(Math.floor(time % 3600 / 60));
        // 计算秒
        var second = handlerTime(Math.floor(time % 3600 % 60));
        // 判断考试时间到自动提交试卷
        if (time == 0) {
            stu_submit_exam()
            clearInterval(timers);
        }
        $(".timer").html(hour + ":" + minutes + ":" + second);
        time--;
    }
    function handlerTime(t) {
        var t1 = t;
        if (t < 10) {
            t1 = "0" + t;
        }

        return t1;
    }

    var timers = setInterval("timer();", 1000);

    $(function () {
        $('li.option label').click(function () {
            var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
            var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
            // 设置已答题
            if (!cardLi.hasClass('hasBeenAnswer')) {
                cardLi.addClass('hasBeenAnswer');
            }
        });
        $('li.option input').click(function () {
            var examId = $(this).closest('.test_content_nr_main').closest('li').attr('id'); // 得到题目ID
            var cardLi = $('a[href=#' + examId + ']'); // 根据题目ID找到对应答题卡
            // 设置已答题
            if (!cardLi.hasClass('hasBeenAnswer')) {
                cardLi.addClass('hasBeenAnswer');
            }
        });
    });

    var stu_submit_exam = function () {
        $('#stu-exam-form').form('submit', {
            url: 'studentExam.do?method=stuCommitExam',
            success: function (data) {
                alert(data);
                exam_window_close();
            }
        })
    };
    var exam_window_close=function(){
        window.opener = null;
        window.open('', '_self');
        window.close();
    };
</script>
<div style="text-align:center;margin:50px 0; font:normal 14px/24px 'MicroSoft YaHei';">
</div>
</body>
</html>
