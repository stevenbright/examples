<%@ tag body-content="tagdependent" isELIgnored="false" %>
<%@ attribute name="map" required="true" type="java.util.Map" %>
<%@ attribute name="nameHeader" required="false" %>
<%@ attribute name="valueHeader" required="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<c:if test="${empty nameHeader}">
    <c:set var="nameHeader" scope="page" value="Key"/>
</c:if>
<c:if test="${empty valueHeader}">
    <c:set var="valueHeader" scope="page" value="Value"/>
</c:if>

<table class="parameters">
    <tr>
        <td>${nameHeader}</td>
        <td>${valueHeader}</td>
    </tr>

    <c:forEach var="entry" items="${map}">
        <tr>
            <td>${entry.key}</td>
            <td>${entry.value}</td>
        </tr>
    </c:forEach>
</table>
