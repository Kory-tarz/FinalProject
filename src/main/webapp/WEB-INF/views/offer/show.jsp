<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Stwórz ofertę</title>
</head>
<body>
<div>
    <h2>Moje przedmioty: </h2>
    <ul>
        <c:forEach items="${sessionScope.offer.submittedItems}" var="myItems">
            <li>${myItems.name}</li>
        </c:forEach>
    </ul>
    <p><a href="<c:url value="/item/list"/>">Dodaj przedmiot</a></p>
    <h2>Przedmioty: ${sessionScope.offer.receivingUser.username}</h2>
    <ul>
        <c:forEach items="${sessionScope.offer.offeredItems}" var="hisItems">
            <li>${hisItems.name}</li>
        </c:forEach>
    </ul>
    <p><a href="<c:url value="/item/list/${sessionScope.offer.receivingUser.id}"/>">Poproś o kolejny przedmiot</a></p>
</div>
</body>
</html>
