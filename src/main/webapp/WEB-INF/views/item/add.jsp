<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" enctype="multipart/form-data">
    <label>Photos: </label>
    <input type="file" name="image" accept="image/png, image/jpeg" />
    <button type="submit" value="Save">Save</button>
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


</body>
</html>
