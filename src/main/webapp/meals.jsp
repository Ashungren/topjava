<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {color: green}
        .exceeded {color: red}
    </style>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Meal list</h2>
    <table border="1" cellpadding="8" cellspacing="8">
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
        </tr>
        <c:forEach items="${mealList}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
            <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                <td>
                    <fmt:parseDate value="${meal.dateTime}" pattern="y-M-dd'T'H:m" var="parsedDate"/>
                    <fmt:formatDate value="${parsedDate}" pattern="yyyy.MM.dd HH:mm"/>
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
    <a href="meals?action=create">Add meal</a>
</body>
</html>