<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meal</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h2>Edit meal</h2>
    <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
    <form method="post" action="meals">
        <input type="hidden" name="id" value="${meal.id}">
        <dl>
            <dt>Date</dt>
            <dd><input type="datetime-local" value="${meal.dateTime}" name="dateTime"></dd>
        </dl>
        <dl>
            <dt>Description</dt>
            <dd><input type="text" value="${meal.description}" size="30" name="description"></dd>
        </dl>
        <dl>
            <dt>Calories</dt>
            <dd><input type="number" value="${meal.calories}" name="calories"></dd>
        </dl>
        <button type="submit">Save</button>
        <button onclick="window.history.back()">Cancel</button>
    </form>
</body>
</html>
