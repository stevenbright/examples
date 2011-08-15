<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/tiles/page-header.jsp"/>
</head>

<h2>Hello Response</h2>

<p>${helloResponse}</p>

<p>Back to <a href="<c:url value="/hello-form"/>">hello form</a>.</p>

</html>
