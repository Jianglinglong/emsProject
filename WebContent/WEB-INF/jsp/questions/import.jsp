<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>
	<style>
		#imp {
			margin-top:20px;
			margin-left:20px;
		}
	</style>
	<form id="imp" method="post" enctype="multipart/form-data">
		<input id="fb" type="text" name="excel" style="width:300px">
	</form>
	<script type="text/javascript">
		$(function() {
			$('#fb').filebox({    
			    buttonText: '选择导入的excel文件', 
			    buttonAlign: 'right',
			    height:40,
			})
		});
	</script>
</body>
</html>