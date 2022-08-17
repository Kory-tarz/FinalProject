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

    <c:if test="${status.myOffer}">
        <c:if test="${!status.receivedByMe && !status.receivedByOther}">
            <form:form modelAttribute="offer" action="/offer/withdraw/">
                <%@include file="hidden_offer_attributes.jsp" %>
                <button type="submit" style="color: red">Anuluj wymianę</button>
            </form:form>
        </c:if>
        <c:if test="${!status.receivedByMe}">
            <form:form modelAttribute="offer" action="/offer/transaction/confirmation/">
                <%@include file="hidden_offer_attributes.jsp" %>
                <button type="submit" style="color: blue">Potwierdź otrzymanie przedmiotów</button>
            </form:form>
        </c:if>
        <c:if test="${status.receivedByMe}">
            <p style="color: green">Potwierdziłeś otrzymanie przedmiotów</p>
        </c:if>
        <c:if test="${status.receivedByOther}">
            <p style="color: green">Użytkownik potwierdził otrzymanie od Ciebie przedmiotów</p>
        </c:if>
        <c:if test="${!status.receivedByOther}">
            <p style="color: blue">Oczekiwanie aż użytkownik potwierdzi otrzymanie przedmiotów</p>
        </c:if>
    </c:if>

    <p><a href="<c:url value="/offer/history/${offer.id}"/>">Zobacz historię</a></p>
</div>
</body>
</html>
