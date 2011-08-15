<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="login.title" /></title>
    <style type="text/css">
        .error {
            color: #ff0000;
        }
    </style>
</head>
<body>

<h3><spring:message code="login.header"/></h3>

<!-- error message -->
<c:if test="${not empty param.error}">
    <p class="error">
        <spring:message code="login.error"/>
    </p>
</c:if>

<p id="user-warning" class="error" style="display:none">
    <spring:message code="login.nouser" />
</p>

<form action="loginsubmit" method="post">
    <table>
        <%-- username --%>
        <tr>
            <td>
                <label for="j_username">
                    <spring:message code="login.label.username"/>
                </label>
            </td>
            <td>
                <input type="text"
                       id="j_username"
                       name="j_username"
                       value="<c:if test="${not empty param.error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}" escapeXml="false"/></c:if>"/>
            </td>
        </tr>
        <%-- password --%>
        <tr>
            <td>
                <label for="j_password"><spring:message code="login.label.password"/></label>
            </td>
            <td><input type="password" id="j_password" name="j_password" /></td>
        </tr>
        <%-- remember me --%>
        <tr>
            <td>
                <label for="_spring_security_remember_me">
                    <spring:message code="login.label.remember" />
                </label>
            </td>
            <td>
                <input id="_spring_security_remember_me"
                       name="_spring_security_remember_me"
                       type="checkbox"
                       value="true"/>
            </td>
        </tr>
    </table>

    <input type="submit" value="<spring:message code="login.submit"/>"/>
</form>
<p><spring:message code="login.create" htmlEscape="false" /></p>
</body>
</html>