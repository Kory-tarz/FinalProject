<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lista Ofert</title>
</head>
<body>
    <%@include file="pick_offer.jsp"%>
    <h2> Złożone oferty </h2>
    <ul>
    <c:forEach items="${offers}" var="offer">
        <li> Oferta dla ${offer.receivingUser.username} na ${offer.offeredItems.size()} przedmiotów </li>
        <p><a href="<c:url value="/offer/details/${offer.id}"/>"> Wyświetl szczegóły </a></p>
    </c:forEach>
    </ul>
</body>
</html>
