<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dodaj nowy przedmiot</title>
    <%@include file="navbar.jsp"%>
<h2>Dodaj nowy przedmiot</h2>
<c:if test="${error_msg != null}">
    <label style="color: red">${error_msg}</label>
</c:if>
<form:form modelAttribute="item" enctype="multipart/form-data">
    <form:hidden path="owner"/>
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
        <label>Dodaj zdjęcia:</label>
        <input type="file" name="images" multiple required>
    </div>
    <button type="submit">Dodaj przedmiot</button>
</form:form>
</body>
</html>
