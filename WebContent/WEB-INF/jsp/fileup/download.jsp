<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jstl.jspf" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>下载文件</title>
</head>
<body>
	<div style="margin:50px auto;">
		<table style="font-size:20px;line-height:20px;margin:0px auto;">
		<tr>
			<th>文件名</th>
			<th>操作</th>
		</tr>
		<c:forEach items="${files }" var="file">
		<tr>
			<td>${file.name }</td>
			<td><a href="${basePath }/uploadFile.do?method=downloadFile&&fileName=${file.name }">下载</a></td>
		</tr>
		</c:forEach>
	</table>
	</div>
	
</body>
</html>