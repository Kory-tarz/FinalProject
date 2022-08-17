<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lista Ofert</title>
    <%@include file="navbar.jsp"%>
<%@include file="pick_offer.jsp"%>
<h2> Zakończone wymiany </h2>
<ul>
    <c:forEach items="${offers}" var="offer">
        <li> Oferta od ${offer.submittingUser.username} na ${offer.submittedItems.size()} przedmiotów </li>
        <p><a href="<c:url value="/offer/accepted_details/${offer.id}"/>"> Wyświetl szczegóły </a></p>
    </c:forEach>
</ul>
</body>
</html>
