<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Panel użytkownika</title>
</head>
<body>
    <p><a href="<c:url value="/user/details/${user.id}"/>">Zobacz profil</a></p>
    <p><a href="<c:url value="/item/search"/>">Szukaj przedmiotów</a></p>
    <p><a href="<c:url value="/offer/list"/>">Przejrzyj swoje oferty</a></p>
    <p><a href="<c:url value="/item/list"/>">Zobacz swoje przedmioty</a></p>
    <p><a href="<c:url value="/logout"/>">Wyloguj się</a></p>
</body>
</html>
