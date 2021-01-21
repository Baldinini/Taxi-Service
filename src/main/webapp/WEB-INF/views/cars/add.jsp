<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creation car</title>
</head>
<body>
<h1>Creation car</h1>
<form method="post" action="${pageContext.request.contextPath}/cars/add">
    Provide car model <input type="text" name="car_model">
    Provide manufacturer id <input type="text" name="manufacturer_id">

    <button type="submit">Creation</button>
</form>
</body>
</html>
