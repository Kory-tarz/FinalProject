<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <%@include file="navbar.jsp"%>
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
    <c:if test="${sessionScope.userId == offer.submittingUser.id}">
        <div>
            <form:form modelAttribute="offer" action="/offer/edit/">
                <%@include file="hidden_offer_attributes.jsp"%>
                <button type="submit">Edytuj Ofertę</button>
            </form:form>
        </div>
    </c:if>
    <c:if test="${sessionScope.userId == offer.receivingUser.id}">
        <div>
            <form:form modelAttribute="offer" action="/offer/accept/">
                <%@include file="hidden_offer_attributes.jsp"%>
                <button type="submit">Akceptuj Ofertę</button>
            </form:form>
        </div>
        <div>
            <form:form modelAttribute="offer" action="/offer/negotiate/">
                <%@include file="hidden_offer_attributes.jsp"%>
                <button type="submit">Negocjuj Ofertę</button>
            </form:form>
        </div>
    </c:if>
    <p><a href="<c:url value="/offer/history/${offer.id}"/>">Zobacz historię</a></p>
</div>
</body>
</html>
