<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <h2> Przedmioty ${offer.submittingUser.username}: </h2>
    <ul>
        <c:forEach items="${offer.submittedItems}" var="submittedItem">
            <li>${submittedItem.name}</li>
            <p><a href="<c:url value="/item/details/${submittedItem.id}"/>"> Wyświetl szczegóły </a></p>
        </c:forEach>
    </ul>

    <h2> Przedmioty ${offer.receivingUser.username}:</h2>
    <ul>
        <c:forEach items="${offer.offeredItems}" var="offeredItem">
            <li>${offeredItem.name}</li>
            <p><a href="<c:url value="/item/details/${offeredItem.id}"/>"> Wyświetl szczegóły </a></p>
        </c:forEach>
    </ul>

    <form:form modelAttribute="offer" action="/offer/withdraw/">
        <%@include file="hidden_offer_attributes.jsp" %>
        <button type="submit" style="color: red">Anuluj wymianę</button>
    </form:form>

</div>
</body>
</html>
