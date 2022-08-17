<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Historia</title>
    <%@include file="navbar.jsp"%>
<h1> Historia wymiany </h1>
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
    <c:if test="${offer.previousVersion != null}">
        <form method="get" action="<c:url value="/offer/history/${offer.previousVersion.id}"/>">
            <button type="submit"> &lt;&lt;Poprzednia wersja </button>
        </form>
    </c:if>
    <c:if test="${offer.nextVersion != null}">
        <form method="get" action="<c:url value="/offer/history/${offer.nextVersion.id}"/>">
            <button type="submit"> Następna wersja &gt;&gt;</button>
        </form>
    </c:if>
</div>
</body>
</html>
