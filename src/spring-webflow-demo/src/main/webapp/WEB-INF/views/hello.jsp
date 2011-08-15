<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>--%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Spring MVC 3 Webflow</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css" />" />
</head>
<body>
<jsp:include page="navigation.jsp"/>

<h2>Hello page</h2>
<p>Message is: ${message}</p>
</body>
</html>