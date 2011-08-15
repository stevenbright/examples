<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Person list</title>

    <link rel="stylesheet" type="text/css" href="static/css/style.css" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"> 

    <script type="text/javascript" src="static/js/jquery.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
        })
    </script>
</head>

<body>
<h2>Sample Spring + Spring MVC + Hibernate + JPA Web App</h2>
<h3>Person list</h3>

<ul>
    <li><a href="hello">Hello Page</a></li>
    <li><a href="add">Add Person</a></li>
</ul>

<table class="person-list" cellspacing="0" cellpadding="0">
    <tbody>
    <%--@elvariable id="persons" type="java.util.List"--%>
    <c:forEach var="person" items="${persons}">
        <tr>
            <td class="name-cell">${person.name}</td>
            <td>${person.age}</td>
            <td class="age-cell">${person.subscribedToPosts}</td>
            <td>${person.status}</td>
        </tr>
    </c:forEach>
    </tbody>

</table>

</body>
</html>
