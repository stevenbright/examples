<%@ tag body-content="scriptless" isELIgnored="false" %>
<%@ attribute name="sectionName" required="true" type="java.lang.String" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <title><spring:message code="title.prefix" /> &raquo; ${sectionName}</title>

    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>" />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.5.2/jquery.min.js"></script>
    <script type="text/javascript" src="<c:url value="/static/js/ns.js"/>"></script>
</head>
<body>

<div id="header">
    <ul class="hor-list user">
        <sec:authorize access="isAuthenticated()">
            <li>
                <spring:message code="label.loginGreeting" />&nbsp;
                <a href="<c:url value="/profile/view.html" />"><sec:authentication property="principal.username" /></a>!
            </li>
            <li><a href="<c:url value="/logout.do"/>"><spring:message code="label.logout" /></a></li>
        </sec:authorize>
        <sec:authorize access="!isAuthenticated()">
            <li><a href="<c:url value="/login.html"/>"><spring:message code="label.login" /></a></li>
        </sec:authorize>
    </ul>
</div>

<div id="wrapper">
    <h2>${sectionName}</h2>
    <jsp:doBody/>
</div>
</body>
</html>
