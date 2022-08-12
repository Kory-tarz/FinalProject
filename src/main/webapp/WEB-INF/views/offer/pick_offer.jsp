<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <form action="<c:url value="/offer/list/submitted"/>" method="get">
        <button type="submit">Wysłane oferty</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <form action="<c:url value="/offer/list/received"/>" method="get">
        <button type="submit">Otrzymane oferty</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <form action="<c:url value="/offer/list/accepted"/>" method="get">
        <button type="submit">Zaakceptowane oferty</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>


