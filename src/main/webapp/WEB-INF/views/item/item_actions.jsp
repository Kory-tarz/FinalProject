<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--@elvariable id="item" type="pl.cyryl.finalproject.app.item.Item"--%>
<c:if test="${item.active}">
    <c:if test="${sessionScope.offer != null && sessionScope.userId == item.owner.id}">
        <a href="<c:url value="/offer/add/${item.id}"/>"> Dodaj do wymiany </a>
    </c:if>
    <c:if test="${sessionScope.offer != null && sessionScope.offer.receivingUser.id == item.owner.id}">
        <a href="<c:url value="/offer/add/${item.id}/${item.owner.id}"/>"> Poproś o wymianę </a>
    </c:if>
    <c:if test="${sessionScope.offer == null && sessionScope.userId != item.owner.id}">
        <a href="<c:url value="/offer/create/${item.id}"/>"> Złóż propozycję wymiany </a>
    </c:if>
</c:if>
