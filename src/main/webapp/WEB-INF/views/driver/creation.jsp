
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creation driver</title>
</head>
<body>
<h1>Provide drivers details</h1>

<form method="post" action="${pageContext.request.contextPath}/driver/creation">
    Provide driver name <input type="text" name="driver_name">
    Provide driver licence number <input type="text" name="licence_number">

    <button type="submit">Creation</button>
</form>
</body>
</html>
