<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />

    <title><spring:message code="title.prefix" /> &raquo; <spring:message code="label.login" /></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/static/css/style.css"/>" />
</head>
<body>

<a href="<c:url value="/index.html" />">
    <spring:message code="label.index" />
</a><br/>

<c:if test="$\{not empty param.error\}">
    <p style="color: red">
        <spring:message code="message.loginError" /> : $\{sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message\}
    </p>
</c:if>

<form method="POST" action="<c:url value="/j_spring_security_check" />">
    <table>
        <tr>
            <td><label for="username-input"><spring:message code="label.login" /></label></td>
            <td><input id="username-input" type="text" name="j_username" /></td>
        </tr>
        <tr>
            <td><label for="password-input"><spring:message code="label.password" /></label></td>
            <td><input id="password-input" type="password" name="j_password" /></td>
        </tr>
        <tr>
            <td><label for="remember-me-input"><spring:message code="label.remember" /></label></td>
            <td><input id="remember-me-input" type="checkbox" name="_spring_security_remember_me" /></td>
        </tr>
        <tr>
            <td colspan="2" align="right">
                <input type="submit" value="Login" />
                <input type="reset" value="Reset" />
            </td>
        </tr>
    </table>
</form>

</body>
</html>