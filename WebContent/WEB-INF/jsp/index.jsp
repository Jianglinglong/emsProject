<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="copyright" content="All Rights Reserved, Copyright (C) 2013, Wuyeguo, Ltd."/>
    <title>考试管理中心</title>
    <%@include file="/common/easyui.jspf"%>
    <link rel="stylesheet" type="text/css" href="${basePath }/css/wu.css"/>
    <script type="text/javascript" src="${basePath }/js/menu.js"></script>
    <script type="text/javascript" src="${basePath}/js/easyui-tabs/tabsmenu.js"></script>

    <script type="text/javascript">
        var menu = ${menuJson};
        if(menu == ""){
            alert("你没有权限，请于管理员联系");
        }
        if (menu != null) {
            _menus = menu;
        }
        $(function () {
            $('#wu-tabs').contextMenus();
//            $('#wu-tabs').tabs('bindDblclick',function (index, title) {
//                if (index>0){
//                    $('#wu-tabs').tabs('close',title);
//                }
//            })
        })
    </script>
</head>
<body class="easyui-layout">
<!-- begin of header -->
<div class="wu-header" data-options="region:'north',border:false,split:true">
    <div class="wu-header-left">
        <h1>在线考试管理系统</h1>
    </div>
    <div class="wu-header-right">
        <p><strong class="easyui-tooltip" title="2条未读消息">${userName }</strong>，欢迎您！</p>
        <p><a href="${basePath}/login.jsp">网站首页</a>|<a href="javascript:;">帮助中心</a>|<a href="javascript:safeExit();">安全退出</a></p>
    </div>
</div>
<!-- end of header -->
<!-- begin of sidebar -->
<div class="wu-sidebar" data-options="region:'west',split:true,border:true,title:'导航菜单'">
    <div id="wnav" class="easyui-accordion" data-options="border:false,fit:true">
        <!-- 动态加载菜单 -->
    </div>
</div>
<!-- end of sidebar -->
<!-- begin of main -->
<div class="wu-main" data-options="region:'center'">
    <div id="wu-tabs" class="easyui-tabs" data-options="border:false,fit:true">
        <div title="首页" data-options="closable:false,iconCls:'icon-tip',cls:'pd3'" >
	        <h1 style="color:red;margin: 50px 450px;">欢迎使用本系统！</h1>
	        <%--<span style="width: 300px;height: 300px;display:block;position: absolute;top: 50px;right: 50px;background: url(${basePath}/images/huaji.jpg) no-repeat;background-size: cover;"></span>--%>
	        <p style="font-size: 50px;margin-left: 280px;margin-top: 285px;">第六组，你值得拥有！</p>
        </div>
    </div>
</div>
<!-- end of main -->
<!-- begin of footer -->
<div class="wu-footer" data-options="region:'south',border:true,split:true">
    &copy; 2017  All Rights Reserved
</div>
<!-- end of footer -->

</body>
<script>
	function safeExit(){
		 $.messager.confirm('信息提示', '确定要退出吗？', function(result) {
			 if(result){
				 $.get('login.do?method=saveExit',function(data){
					 if(data == "OK"){
						$.messager.alert('','退出成功!','info',function(){
							window.location.href ='login.jsp';
						});
					 } else {
						 $.messager.alert("提示信息", "退出失败!");
					 }
				 });
			 }
		 });
	}
</script>

</html>
