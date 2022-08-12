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
            <p><a href="<c:url value="/item/details/${item.id}"/>"> Wyświetl szczegóły </a></p>
            <%@include file="item_actions.jsp"%>
        </li>
    </c:forEach>
</ul>
<p><a href="<c:url value="/item/add/"/>"> Dodaj swój przedmiot </a></p>
</body>
</html>
