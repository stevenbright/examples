<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:contentWrapper sectionName="Oauth Callback">
    <h3>OAuth Callback</h3>
    <p>See URL parameters for details.</p>

    <c:if test="${error != null}">
        <p class="message error">${error}</p>
        <p>Proceed to <a href="<c:url value="/login.html" />">Login</a> and try again.</p>
    </c:if>

    <c:if test="${code != null}">
        <p class="message info">Google code = ${code}</p>

        <script type="text/javascript" src="<c:url value="/static/js/log.js"/>"></script>


        <hr/>
        <div>
            <button id="clear-log">Clear Log</button>
        </div>

        <ul id="log-holder">
        </ul>
    </c:if>

</tag:contentWrapper>
