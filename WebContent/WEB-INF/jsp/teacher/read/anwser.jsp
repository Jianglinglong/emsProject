<%--
  Created by IntelliJ IDEA.
  User: Direct
  Date: 2017/10/14
  Time: 8:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/easyui.jspf" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form id="answer-paper-form" action="${basePath}/readPaper.do?method=doReadPaper" method="post">
    <table>
        <tr>
            <input type="hidden" readonly="readonly" name="answerId" value="${answer.answerId}"/>
            <th colspan="2" align="center">学生：</th>
            <input  type="hidden" name="stuId" value="${answer.stuId}">
            <td colspan="2">
                ${student}
            </td>
        </tr>
        <tr>
            <th><p>试卷：</p></th>
            <td>
                <input type="hidden" readonly name="paperId" value="${answer.paperId}">
                ${paperName}
            </td>
            <th>客观题得分：</th>
            <td>
                ${stuScore.autoScore}
                <input name="scoreId" hidden value="${stuScore.scoreId}"/>
            </td>
        </tr>
        <%--<tr>--%>
            <%--<th colspan="4" align="center"> </th>--%>
        <%--</tr>--%>
        <c:forEach var="sutSub" items="${stuSubAnswer}">
            <tr>
                <th>主观题目</th>
                <td colspan="3"><textarea style="width: 100%">${sutSub.title}</textarea> </td>
            </tr>
            <tr>
                <th>学生答案</th>
                <td colspan="3"><textarea style="width: 100%">${sutSub.checked}</textarea></td>
            </tr><tr>
                <th>参考答案</th>
                <td colspan="3"><textarea style="width: 100%">${sutSub.answer}</textarea></td>
            </tr>
            <tr>
                <th>得分(满分：${sutSub.score})</th>
                <td><input class="easyui-numberbox" name="${sutSub.id}" data-options="required:true,min:0,max:${sutSub.score},precision:2"></td>
            </tr>
        </c:forEach>
    </table>
</form>


</body>
</html>
