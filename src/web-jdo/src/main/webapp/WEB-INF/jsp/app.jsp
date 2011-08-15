<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title>App</title>
</head>
<body>
<h2>WebJDO sample</h2>

<p>Logged in as <c:out value="${username}"/></p>

<ul>
    <li><a href="logout"><spring:message code="global.logout"/></a></li>
    <li><a href="hello"><spring:message code="app.hello"/></a></li>
</ul>

</body>
</html>
