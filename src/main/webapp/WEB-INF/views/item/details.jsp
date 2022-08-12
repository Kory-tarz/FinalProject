<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div>
    <h3>Zdjęcia</h3>
    <ul>
        <c:forEach items="${item.itemPhotos}" var="photo">
            <li><img src="${dirName}${photo.imagePath}" height="100" width="150"></li>
        </c:forEach>
    </ul>
</div>
<div>
    <label>Nazwa</label>
    <div> ${item.name} </div>
</div>
<div>
    <label>Opis</label>
    <div> ${item.description} </div>
</div>
<div>
    <label>Oczekiwania</label>
    <div> ${item.expectations} </div>
</div>
<div>
    <label>Kategoria</label>
    <div> ${item.category.name} </div>
</div>
<c:if test="${sessionScope.userId == item.owner.id}">
    <div>
        Tutaj link do edycji itemu
    </div>
</c:if>
<%@include file="item_actions.jsp"%>
</body>
</html>
