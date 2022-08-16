<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Strona główna</title>
</head>
<body>
    <c:if test="${sessionScope.userId != null}">
        <p><a href="<c:url value="/user/panel"/>">Przejdź do serwisu</a></p>
    </c:if>
    <c:if test="${sessionScope.userId == null}">
        <p><a href="<c:url value="/login"/>">Zaloguj się</a></p>
        <p><a href="<c:url value="/register"/>">Zarejstruj się</a></p>
    </c:if>
</body>
</html>
