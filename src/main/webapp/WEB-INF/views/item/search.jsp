<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wyszukiwanie przedmiotów</title>
</head>
<body>
<form method="get">
    <label>Liczba przedmiotów na stonę
        <input type="hidden" name="page_nr" value="0">
        <input type="number" name="items_per_page" min="5" max="100"/>
        <button type="submit">Szukaj</button>
    </label>
</form>
<table>
    <tr>
        <th>Zdjęcie</th>
        <th>Nazwa</th>
        <th>Kategoria</th>
        <th>Akcja</th>
    </tr>
    <c:forEach items="${items.content}" var="item">
        <tr>
            <td><img src="${dirName}${item.mainPhoto.imagePath}" height="60" width="60"></td>
            <td>${item.name}</td>
            <td>${item.category.name}</td>
            <td>
                <form action="<c:url value="/item/details/${item.id}"/>" method="get">
                    <button type="submit">Wyświetl szczegóły</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<div>
    <label>Strona ${items.pageable.pageNumber + 1}</label>
    <c:if test="${items.hasNext()}">
        <form method="get">
            <input type="hidden" name="items_per_page" value="${items.pageable.pageSize}">
            <input type="hidden" name="page_nr" value="${items.pageable.pageNumber + 1}">
            <button type="submit"> >> </button>
        </form>
    </c:if>
    <c:if test="${items.hasPrevious()}">
        <form method="get">
            <input type="hidden" name="items_per_page" value="${items.pageable.pageSize}">
            <input type="hidden" name="page_nr" value="${items.pageable.pageNumber - 1}">
            <button type="submit"> << </button>
        </form>
    </c:if>
</div>
</body>
</html>
