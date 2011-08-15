<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Main">
    <p>Hello there!</p>
    <p>Go to <a href="<c:url value="/hello.html"/>">greeting page</a>.</p>
    <p>Go to <a href="<c:url value="/profile/view.html"/>">profile page</a>.</p>
    <p>Go to <a href="<c:url value="/admin/main.html"/>">admin console</a>.</p>
</tag:pageWrapper>