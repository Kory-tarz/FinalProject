<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <h2>Właściciel: ${item.owner.username}</h2>
    <a href="<c:url value="/user/details/${item.owner.id}"/>">Zobacz profil</a>
</div>
<div>
    <h3>Zdjęcia</h3>
    <ul>
        <c:forEach items="${item.itemPhotos}" var="photo">
            <li><img src="${dirName}${photo.imagePath}" height="100" width="150"></li>
        </c:forEach>
    </ul>
</div>
<div>
    <label>Nazwa: </label> ${item.name}
</div>
<div>
    <label>Opis: </label> ${item.description}
</div>
<div>
    <label>Oczekiwania: </label>${item.expectations}
</div>
<div>
    <label>Kategoria</label>
    <div> ${item.category.name} </div>
</div>
<c:if test="${item.active}">
    <c:if test="${sessionScope.userId == item.owner.id}">
        <div>
            <p><a href="<c:url value="/item/edit/${item.id}"/>">Edytuj</a></p>
        </div>
    </c:if>
    <%@include file="item_actions.jsp"%>
</c:if>
<c:if test="${!item.active}">
    <p style="color: chocolate">PRZEDMIOT NIEAKTYWNY</p>
</c:if>
</body>
</html>
