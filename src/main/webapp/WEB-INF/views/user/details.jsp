<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profil użytkownika ${user.username}</title>
</head>
<body>
<div>
    <h2>Nazwa użytkownika: ${user.username}</h2>
</div>
<div>
    <img src="${dirName}${user.profilePhotoFile.imagePath}" width="100" height="100">
</div>
<div>
    <h2>O sobie: ${user.about}</h2>
</div>
<div>
    <h3>Data dołączenia: ${user.creationDate}</h3>
</div>
</body>
</html>