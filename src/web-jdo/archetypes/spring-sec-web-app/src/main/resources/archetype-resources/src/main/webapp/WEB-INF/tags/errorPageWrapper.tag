<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ attribute name="errorUniform" required="true" type="java.lang.String" %>
<%@ attribute name="errorDescription" required="true" type="java.lang.String" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><spring:message code="title.prefix" /> &raquo; ${errorUniform}</title>
</head>
<body>
<h2>${errorUniform}</h2>
<p>${errorDescription}</p>
</body>
</html>
