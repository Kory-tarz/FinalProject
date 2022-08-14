<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wyszukiwanie przedmiotów</title>
</head>
<body>
<form method="get" id="search-form">
    <input type="hidden" name="page_nr" value="0">
    <label>Liczba przedmiotów na stonę
        <input type="number" name="items_per_page" min="5" max="100" value="${items.pageable.pageSize}"/>
    </label>
    <label>Kategoria
        <select name="category">
            <option value="0">---</option>
            <c:forEach items="${categories}" var="category">
                <option value="${category.id}">${category.name}</option>
            </c:forEach>
        </select>
    </label>
    <label> sortuj po
        <select name="sort">
            <option value="id">---</option>
            <option value="name"> Nazwa</option>
            <option value="date"> Data dodania</option>
        </select>
    </label>
    <select name="asc">
        <option value="true"> rosnąco</option>
        <option value="false"> malejąco</option>
    </select>
    <button type="submit">Szukaj</button>
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
            <%@include file="pagination_hidden_attributes.jsp" %>
            <input type="hidden" name="page_nr" value="${items.pageable.pageNumber + 1}">
            <button type="submit"> >></button>
        </form>
    </c:if>
    <c:if test="${items.hasPrevious()}">
        <form method="get">
            <%@include file="pagination_hidden_attributes.jsp" %>
            <input type="hidden" name="page_nr" value="${items.pageable.pageNumber - 1}">
            <button type="submit"> <<</button>
        </form>
    </c:if>
</div>
<%--<script type="text/javascript">--%>
<%--    const setSelected = (options, testValue) => options.forEach(opt => {--%>
<%--        if (opt.value === testValue) {--%>
<%--            opt.selected = true;--%>
<%--        }--%>
<%--    });--%>
<%--    const form = document.querySelector('#search-form');--%>
<%--    const categoryOptions = form.querySelectorAll('select[name="category"] option');--%>
<%--    const sortOptions = form.querySelectorAll('select[name="sort"] option');--%>
<%--    const ascOptions = form.querySelectorAll('select[name="asc"] option');--%>

<%--    setSelected(categoryOptions, "${category_id}");--%>
<%--    setSelected(sortOptions, "${sort}");--%>
<%--    setSelected(ascOptions, "${asc}");--%>
<%--</script>--%>
<script src="../../js/searchHelper.js" type="text/javascript"></script>
</body>
</html>
