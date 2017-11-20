<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<c:set var="basePath" value="${pageContext.request.contextPath }" />
<link rel="stylesheet" type="text/css" href="${basePath }/css/wu.css" />
<%@include file="/common/easyui.jspf"%>
</head>
<body>
	<div style="margin:40px auto;">
		<c:forEach items="${allChoice }" var="single">
			<p style=" line-height: 20px;margin-left: 25px;">
				<input type="checkbox" id = "${single.choiceId }" name="sc" value="${single.choiceId }"  />
				<input type="text" id = "score${single.choiceId }" placeholder="输入分数" name="score" size="4" />
				<span style="font-family:'微软雅黑';font-szie:14px;">
					${single.choiceTitle }<br/>
					A、${single.answera }<br/>
					B、${single.answerb }<br/>
					C、${single.answerc }<br/>
					D、${single.answerd }<br/>
				</span>
			</p>
		</c:forEach>
		
		<c:forEach items="${allMultiple }" var="multiple">
			<p style=" line-height: 20px;margin-left: 25px;">
				<input type="checkbox" id = "${multiple.choiceId }"  name="sc" value="${multiple.choiceId }" />
				<input type="text" id = "score${multiple.choiceId }" placeholder="输入分数" name="score" size="4" />
				<span style="font-family:'微软雅黑';font-szie:14px;">${multiple.choiceTitle }<br/>
					A、${multiple.answera }<br/>
					B、${multiple.answerb }<br/>
					C、${multiple.answerc }<br/>
					D、${multiple.answerd }<br/>
				</span>
			</p>
		</c:forEach>
		
		<c:forEach items="${allBlank }" var="blank">
			<p style=" line-height: 20px;margin-left: 25px;">
				<input type="checkbox" id = "${blank.blankId }" name="sc" value="${blank.blankId }" />
				<input type="text" id = "score${blank.blankId }" placeholder="输入分数" name="score" size="4" />
				<span style="font-family:'微软雅黑';font-szie:14px;">${blank.blankTitle }
				</span>
			</p>
		</c:forEach>
		
		<c:forEach items="${allJudge }" var="judge">
			<p style=" line-height: 20px;margin-left: 25px;">
				<input type="checkbox" id = "${judge.judgeId }" name="sc" value="${judge.judgeId }" />
				<input type="text" id = "score${judge.judgeId }" placeholder="输入分数" name="score" size="4" />
				<span style="font-family:'微软雅黑';font-szie:14px;">${judge.judgeTitle }
				</span>
			</p>
		</c:forEach>
		
		<c:forEach items="${allSubjective }" var="subjective">
			<p style=" line-height: 20px;margin-left: 25px;">
				<input type="checkbox" id = "${subjective.subId }" name="sc" value="${subjective.subId }" />
				<input type="text" id = "score${subjective.subId }" placeholder="输入分数" name="score" size="4" />
				<span style="font-family:'微软雅黑';font-szie:14px;">${subjective.subTitle }
				</span>
			</p>
		</c:forEach>
	</div>
</body>
</html>