
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Creation manufacturer</title>
</head>
<body>
<h1>Provide manufacturer details</h1>

<form method="post" action="${pageContext.request.contextPath}/manufacturers/add">
    Provide manufacturer name <input type="text" name="manufacturer_name">
    Provide manufacturer country <input type="text" name="manufacturer_country">

    <button type="submit">Creation</button>
</form>
</body>
</html>
