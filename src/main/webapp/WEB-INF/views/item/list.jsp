<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Przedmioty</title>
</head>
<body>
<ul>
    <c:forEach items="${items}" var="item">
        <li>
            <h2>${item.name}</h2>
            <h3>${item.description}</h3>
            <c:if test="${sessionScope.offer != null && sessionScope.userId == item.owner.id}">
                <a href="<c:url value="/offer/add/${item.id}"/>"> Dodaj do wymiany </a>
            </c:if>
            <c:if test="${sessionScope.offer != null && sessionScope.offer.receivingUser.id == item.owner.id}">
                <a href="<c:url value="/offer/add/${item.id}/${item.owner.id}"/>"> Poproś o wymianę </a>
            </c:if>
            <c:if test="${sessionScope.offer == null && sessionScope.userId != item.owner.id}">
                <a href="<c:url value="/offer/create/${item.id}"/>"> Złóż propozycję wymiany </a>
            </c:if>
        </li>
    </c:forEach>
</ul>
</body>
</html>
