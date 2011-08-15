<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Spring 3.0 MVC: Hello</title>
    <jsp:include page="/WEB-INF/tiles/common-header.jsp"/>
</head>
<body>
    <h1>Say it!</h1>
    <a href="<c:url value="/hello.html"/>">Say Hello</a>
</body>
</html>