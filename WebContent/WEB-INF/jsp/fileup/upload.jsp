<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/common/jstl.jspf" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>文件上传</title>
</head>
<body>
	<!--
	控制上传文档 accept=".csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" 
	选择文件:<input style="font-size:20px;" type="file" id="fileName" name="upload">
	-->
	<div style="width:100%;height:100%;background-image: url(${basePath }/images/bg1.jpg)">
		<div class="easyui-layout" data-options="fit:true">
			<div style="margin:0px auto;margin-top:20%;width: 400px">
				<form id="uploadFile" method="post" enctype="multipart/form-data">
					<p style="font-size:20px;">文件名称:<input id="upload-txt" name="upload" style="width: 300px" accept=".csv, application/vnd.ms-excel, application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"></p>
					<!-- <p style="font-size:18px;">文件名称:<input style="font-size:20px;" type="text" id="textName" name="fileName"></p> -->
					<p  style="padding-left:251px;"><input id="btn" class="btn" type="button" style="font-size: 20px;width: 134px;height: 40px;" value="上传"></p>
					
				</form>
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
	$(function () {
        $('#upload-txt').filebox({
            buttonText: '请选择导入的excel文件',
            buttonAlign: 'right',
            height: 40
        })
    });
	
	$("#btn").click(function(){
		$("#uploadFile").form('submit',{
			url:"${basePath }/uploadFile.do?method=uploadFile",
			onSubmit: function(){
				var test = $('#upload-txt').textbox('getText');
				if(test.length==0){
					$.messager.alert('信息提示', '请选择要上传的文件！', 'ok')
					return false;
				}
				
			},    
			success:function(data){
				if(data=="OK"){
					$("#upload-txt").textbox('setText');
					$("#textName").val('');
					$.messager.alert('信息提示', '文件上传成功！', 'ok')
				}
			}		
		});
	});	
	
	</script>
</body>
</html>