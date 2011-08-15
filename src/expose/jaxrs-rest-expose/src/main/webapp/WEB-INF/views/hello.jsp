<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tag" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Spring MVC 3 demo</title>
    <jsp:include page="/WEB-INF/tiles/common-header.jsp"/>
</head>
<body>
    <h1>Spring 3!</h1>
    <tag:helloTag helloMessages="${messages}"/>
</body>
</html>