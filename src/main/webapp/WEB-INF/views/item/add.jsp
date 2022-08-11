<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Dodaj nowy przedmiot</title>
</head>
<body>
<form:form modelAttribute="item">
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
            <form:options value="${categories}" itemLabel="name" itemValue="id"/>
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


</form:form>
</body>
</html>
