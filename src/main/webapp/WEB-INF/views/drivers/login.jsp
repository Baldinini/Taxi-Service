<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>Login page</h1>

<h4 style="color: red">${errorMessage}</h4>

<form action="${pageContext.request.contextPath}/login" method="post">
    Enter your login <input type="text" name="login"><br>
    Enter your password <input type="password" name="password">

    <button type="submit">Login</button><br>

    <a href="${pageContext.request.contextPath}/drivers/add">Creation your driver</a>
</form>
</body>
</html>
