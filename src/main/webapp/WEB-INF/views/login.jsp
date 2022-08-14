<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<h1>Zaloguj się</h1>
<c:if test="${error_msg != null}">
    <h2 style="color: red">${error_msg}</h2>
</c:if>
<form method="post">
    <div><label> Email: <input type="text" name="username"/></label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <div><input type="submit" value="Sign In"/></div>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>
