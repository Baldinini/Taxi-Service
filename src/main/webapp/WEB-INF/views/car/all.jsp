<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All cars</title>
</head>
<body>
<h1>All cars with their manufacturer and drivers</h1>

<table border="1">
    <tr>
        <th>ID</th>
        <th>Model</th>
        <th>Manufacturer name</th>
        <th>Manufacturer country</th>
        <th>Driver name</th>
        <th>Driver licence number</th>
    </tr>
    <c:forEach var="car" items="${cars}">
    <tr>
        <td>
            <c:out value="${car.id}"/>
        </td>
        <td>
            <c:out value="${car.model}"/>
        </td>
        <td>
            <c:out value="${car.manufacturer.name}"/>
        </td>
        <td>
            <c:out value="${car.manufacturer.country}"/>
        </td>
        <td>
            <table border="0">
        <c:forEach var="driver" items="${car.drivers}">
            <tr>
                <td>
                    <c:out value="${driver.name}"/>
                </td>
            </tr>
        </c:forEach>
            </table>
        </td>
        <td>
            <table border="0">
                <c:forEach var="driver" items="${car.drivers}">
                    <tr>
                        <td>
                            <c:out value="${driver.licenceNumber}"/>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
        <td>
            <a href="${pageContext.request.contextPath}/car/delete?id=${car.id}">Delete</a>
        </td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
