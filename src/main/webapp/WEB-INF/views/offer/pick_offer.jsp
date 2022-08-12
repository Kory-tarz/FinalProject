<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <form action="<c:url value="/offer/list/submitted"/>" method="post">
        <button type="submit">Wysłane oferty</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>

    <form action="<c:url value="/offer/list/received"/>" method="post">
        <button type="submit">Otrzymane oferty</button>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    </form>
</div>


