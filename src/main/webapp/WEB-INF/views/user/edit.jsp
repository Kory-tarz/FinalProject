<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edycja użytkownika</title>
</head>
<body>
<h1>Edytuj dane</h1>
<div>
    <c:if test="${error_msg != null}">
        <label style="color: red">${error_msg}</label>
    </c:if>
</div>
<form:form modelAttribute="user">
    <form:hidden path="email"/>
    <form:hidden path="enabled"/>
    <form:hidden path="password"/>
    <form:hidden path="creationDate"/>
    <div>
        <label>Zdjęcie profilowe</label>
        <c:forEach items="${profile_pictures}" var="picture">
            <img src="${pictureDir}${picture.imagePath}" alt="${picture.path}" width="50" height="50"/>
            <form:radiobutton path="profilePhotoFile" checked="${picture.id == user.profilePhotoFile.id ? 'checked' : ''}" value="${picture}"/>
        </c:forEach>
    </div>
    <div>
        <label>Nazwa użytkownika</label>
        <form:input path="username"/>
        <form:errors path="username"/>
    </div>
    <div>
        <label>Imię</label>
        <form:input path="firstName"/>
        <form:errors path="firstName"/>
    </div>
    <div>
        <label>Nazwisko</label>
        <form:input path="lastName"/>
        <form:errors path="lastName"/>
    </div>
    <div>
        <label>Adres</label>
        <form:input path="address"/>
        <form:errors path="address"/>
    </div>
    <div>
        <label>O sobie:</label>
        <form:textarea path="about"/>
        <form:errors path="about"/>
    </div>
    <input type="submit" value="Potwierdź">
</form:form>
</body>
</html>
