<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edytuj przedmiot</title>
    <%@include file="navbar.jsp"%>
<h2>Edytuj przedmiot</h2>
<p><b style="color: red">Edytowanie przedmiotu spowoduje anulowanie wszystkich ofert w których on się znajduje</b></p>
<c:if test="${error_msg != null}">
    <label style="color: red">${error_msg}</label>
</c:if>
<form:form modelAttribute="item" enctype="multipart/form-data" action="/item/edit">
    <form:hidden path="owner"/>
    <form:hidden path="id"/>
    <form:hidden path="itemPhotos"/>
    <div>
        <label>Nazwa</label>
        <form:input path="name"/>
        <form:errors path="name"/>
    </div>
    <div>
        <label>Opis</label>
        <form:textarea path="description"/>
        <form:errors path="description"/>
    </div>
    <div>
        <label>Kategoria</label>
        <form:select path="category">
            <form:options items="${categories}" itemLabel="name" itemValue="id"/>
        </form:select>
    </div>
    <div>
        <label>Przedmiot publiczny?</label>
        Tak <form:radiobutton path="publicVisibility" value="true"/>
        Nie <form:radiobutton path="publicVisibility" value="false"/>
    </div>
    <div>
        <label>Wymienię na:</label>
        <form:textarea path="expectations"/>
        <form:errors path="expectations"/>
    </div>
    <div>
        <label>Dodaj nowe zdjęcia:</label>
        <input type="file" name="images" multiple>
    </div>
    <button type="submit">Zapisz zmiany</button>
</form:form>
</body>
</html>
