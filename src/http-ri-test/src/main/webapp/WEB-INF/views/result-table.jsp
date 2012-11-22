<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Result">
    <tag:keyValueTable map="${result}" nameHeader="Property Name" valueHeader="Property Value" />
    <p><a href="<c:url value="/index.html"/>">Go Back</a></p>
</tag:pageWrapper>
