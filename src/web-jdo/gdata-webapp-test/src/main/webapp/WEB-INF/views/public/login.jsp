<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:contentWrapper sectionName="Login">
    <p>Logging in using Google OAuth 2.0.</p>

    <%--
            Login sequence is as follows:
            login.html -> oauth.do -> access.do
    --%>

    <%-- Find redirect URL --%>
    <c:set var="r" value="${pageContext.request}" />
    <c:url var="redirectUri" value="${fn:replace(r.requestURL, r.requestURI, '')}${r.contextPath}/oauth.do" />
    <!-- redirect uri = ${redirectUri} -->

    <form action="https://accounts.google.com/o/oauth2/auth" method="GET">
        <input type="hidden" name="client_id" value="${googleClientId}" />
        <input type="hidden" name="scope" value="${googleAppScope}" />
        <input type="hidden" name="redirect_uri" value="${redirectUri}" />
        <input type="hidden" name="response_type" value="code" />
        <input type="hidden" name="state" value="OptionalStateGoesHere" />

        <input type="submit" value="Login"/>
    </form>
</tag:contentWrapper>
