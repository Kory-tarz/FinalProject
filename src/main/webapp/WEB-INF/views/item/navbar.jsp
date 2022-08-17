<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    ul {
        list-style-type: none;
        margin: 0;
        padding: 0;
        overflow: hidden;
    }

    li {
        float: left;
    }

    li a {
        display: block;
        color: black;
        text-align: center;
        padding: 14px 16px;
        text-decoration: none;
    }

</style>
</head>
</body>
<ul>
    <li><a href="<c:url value="/user/details/${sessionScope.userId}"/>">Zobacz profil</a></li>
    <li><a href="<c:url value="/item/search"/>">Szukaj przedmiotów</a></li>
    <li><a href="<c:url value="/offer/list"/>">Przejrzyj swoje oferty</a></li>
    <li><a href="<c:url value="/item/list"/>">Zobacz swoje przedmioty</a></li>
    <li><a href="<c:url value="/offer/show"/>">Aktualna wymiana</a></li>
    <li><a href="<c:url value="/logout"/>">Wyloguj się</a></li>
</ul>
<p></p>


