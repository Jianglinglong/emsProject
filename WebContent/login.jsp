<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/easyui.jspf"%>
<!DOCTYPE html>
<html>

	<head>
		<meta charset="utf-8" />
		<title>登陆界面</title>
		<link rel="stylesheet" type="text/css" href="${basePath }/css/login.css"/>
		<style type="text/css">
			::-webkit-input-placeholder { /* WebKit browsers */
			　　color:#fff;
			}
			:-moz-placeholder { /* Mozilla Firefox 4 to 18 */
			　　color:#fff;
			}
			::-moz-placeholder { /* Mozilla Firefox 19+ */
			　　color:#fff;
			}
			:-ms-input-placeholder { /* Internet Explorer 10+ */
				color:#fff;
			}
		</style>
	</head>
	<body>
		<div class="login_box">
			<form method="post" id="loginform">
				<div class="in_text">
					<span class="icon"><i class="iconfont">&#xe63b;</i></span>
					<input type="text" name="account" id="account" class="in_box" placeholder="用户名"/>
				</div>
				<div class="in_text">
					<span class="icon"><i class="iconfont">&#xe66c;</i></span>
					<input type="password" name="password" id="password" class="in_box" placeholder="用户密码"/>
				</div>
				<div class="in_check">
					<span style="cursor: default;">请选择您的身份：</span>
					<input type="radio" name="user" id="teacher" value="tea" /><label for="teacher">教师</label> &nbsp;&nbsp;
					<input type="radio" name="user" id="student" value="stu" /><label for="student">学生</label>
				</div>
				<div class="in_text" id="valodateBox" style="display:none;">
					<span><input type="text" name="validateCode" id="validateCode" class="in_box" placeholder="请输入验证码" maxlength="4"/></span>
					<span style="position: absolute;right: 1px;">
						<img onclick="f5();" id="pic" title="看不清楚,点击图片刷新" src="validate?method=getValidateCode" align="top" style="height: 104%;border-radius: 0 10px 10px 0px;margin-top: -1px;margin-right: -2px;"/>
					</span>
				</div>
				
				<div class="in_check">
					<a href="javascript:onsubmit();" class="but_submit" id="but_submit">登&nbsp;&nbsp;陆</a>
				</div>

			</form>

		</div>
		<script type="text/javascript">
			function onsubmit(){
				
				$("#loginform").form('submit',{
					url:"login.do?method=logincheck",
					onSubmit:function(){
						var dis = $("#valodateBox").css("display");
						if(dis == "none") {
							$("#validateCode").attr("value","*/*/*");
						}
						var password = $("#password").val();
						password = $.md5(password);
						$("#password").val(password);
						var ra = $("input[type='radio']:checked").val();
						if(ra == null) {
							$.messager.alert('确认','请选择您的身份!');
							return false;
						}
					},
					success:function(data){
						if(data == "OK"){
							window.location.href = "${pageContext.request.contextPath }/login.do?method=initIndex";
						} else if(data == "NO") {
							$.messager.alert('确认','您输入的账户或密码有误!');
							$("#password").val("");
							f5();
						} else if(data == "NV") {
							$.messager.alert('确认','验证码有误，请重新输入!');
							$("#password").val("");
							$("#validateCode").val("");
							f5();
						} else if(data == "NVY") {
							$.messager.alert('确认','您输入的账户或密码有误!'); 
							$("#password").val("");
							$("#valodateBox").css("display","block");
							$("#validateCode").val("");
							f5();
						} 
					}
				});
			}
			
			function f5() {
				$("#pic").attr("src","validate?method=getValidateCode&"+Math.random());
			}
			
		</script>
		<script src="${pageContext.request.contextPath }/js/login.js" type="text/javascript" charset="utf-8"></script>
	</body>

</html>