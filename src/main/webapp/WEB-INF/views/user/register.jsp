<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>
<h1>Rejestracja</h1>
<div>
    <c:if test="${error_msg != null}">
        <label style="color: red">${error_msg}</label>
    </c:if>
</div>
<form:form modelAttribute="user">
    <div>
        <label>Profile picture</label>
        <c:forEach items="${profile_pictures}" var="picture">
            <img src="${pictureDir}${picture.imagePath}" alt="${picture.path}" width="50" height="50"/>
            <form:radiobutton path="profilePhotoFile" value="${picture}"/>
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
        <label>Password</label>
        <form:password path="password"/>
        <form:errors path="password"/>
    </div>
    <div>
        <label>Email</label>
        <form:input path="email"/>
        <form:errors path="email"/>
    </div>
    <div>
        <label>Adres</label>
        <form:input path="address"/>
        <form:errors path="address"/>
    </div>
    <div>
        <label>About</label>
        <form:input path="about"/>
        <form:errors path="about"/>
    </div>
    <input type="submit" value="confirm">
</form:form>
</body>
</html>
