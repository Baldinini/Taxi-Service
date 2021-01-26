
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creation driver</title>
</head>
<body>
<h1>Provide drivers details</h1>

<form method="post" action="${pageContext.request.contextPath}/drivers/add">
    Provide driver name <input type="text" name="driver_name"><br>
    Provide licence number <input type="text" name="licence_number"><br>
    Provide driver login <input type="text" name="login"><br>
    Provide password <input type="password" name="password">

    <button type="submit">Creation</button>
</form>
</body>
</html>
