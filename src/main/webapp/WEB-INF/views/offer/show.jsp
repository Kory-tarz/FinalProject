<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Złóż ofertę</title>
</head>
<body>
<div>
    <c:if test="${error_msg != null}">
        <p style="color: red">${error_msg}</p>
    </c:if>
    <h2>Moje przedmioty: </h2>
    <ul>
        <c:forEach items="${sessionScope.offer.submittedItems}" var="submittedItem">
            <li>${submittedItem.name}</li>
            <p><a href="<c:url value="/offer/remove/${submittedItem.id}"/>">Usuń</a></p>
        </c:forEach>
    </ul>
    <p><a href="<c:url value="/item/list"/>">Dodaj przedmiot</a></p>
    <h2>Przedmioty ${sessionScope.offer.receivingUser.username}:</h2>
    <ul>
        <c:forEach items="${sessionScope.offer.offeredItems}" var="offeredItem">
            <li>${offeredItem.name}</li>
            <p><a href="<c:url value="/offer/remove/${offeredItem.id}"/>">Usuń</a></p>
        </c:forEach>
    </ul>
    <p><a href="<c:url value="/item/list/${sessionScope.offer.receivingUser.id}"/>">Poproś o kolejny przedmiot</a></p>
    <div>
        <form action="<c:url value="/offer/submit"/>" method="post">
            <button type="submit">Złóż ofertę</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
    <div>
        <form action="<c:url value="/offer/cancel"/>" method="post">
            <button type="submit">Anuluj</button>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        </form>
    </div>
</div>
</body>
</html>
