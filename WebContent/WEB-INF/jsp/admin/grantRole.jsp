<%--
  Created by IntelliJ IDEA.
  User: Direct
  Date: 2017/10/18
  Time: 18:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="/common/jstl.jspf" %>
<form id="grant-role-form" method="post">
    <input type="hidden" name="userId" value="${userId}"/>
    <fieldset>
        <legendg>原角色</legendg>
        <c:forEach items="${allRole}" var="role">
            <c:set var="flag" value="true"></c:set>
            <c:forEach items="${oldRole}" var="old">
                <c:if test="${old.roleId == role.roleId}">
                    <input type="checkbox" disabled checked value="${role.roleId}"/>${role.roleName}
                    <c:set var="flag" value="false"></c:set>
                </c:if>
            </c:forEach>
            <c:if test="${flag == true}">
                <input type="checkbox" disabled value="${role.roleId}"/>${role.roleName}
            </c:if>
        </c:forEach>
    </fieldset>

    <fieldset>
        <legendg>新角色</legendg>
            <c:forEach items="${allRole}" var="role">
                <input type="checkbox" name="roles" value="${role.roleId}"/>${role.roleName}
            </c:forEach>
    </fieldset>
</form>
