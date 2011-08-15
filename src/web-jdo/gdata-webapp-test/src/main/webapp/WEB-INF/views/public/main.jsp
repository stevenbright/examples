<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:contentWrapper sectionName="Main">
    <h3>Hello there!</h3>
    <p>It's good that this page is ever displayed.</p>
    <p>Place additional content if you wanna change it :)</p>

    <ul>
        <li><a href="<c:url value="/login.html" />">Login</a></li>
    </ul>
</tag:contentWrapper>
