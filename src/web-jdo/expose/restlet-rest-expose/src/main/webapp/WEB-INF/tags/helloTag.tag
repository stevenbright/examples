<%@ tag body-content="tagdependent" isELIgnored="false" %>
<%@ attribute name="helloMessages" required="true" type="java.lang.String[]" %>
<%@ attribute name="headerMessage" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${empty headerMessage}">
    <c:set var="nameHeader" scope="page" value="Hello Tag!"/>
</c:if>

<h2>${headerMessage}</h2>
<h3>Hello messages:</h3>
<ul>
    <c:forEach var="message" items="${helloMessages}">
        <li>${message}</li>
    </c:forEach>
</ul>
