<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/tiles/page-header.jsp"/>
</head>

<table>
    <tr>
        <td>Index (this page)</td>
        <td><a href="<c:url value="/index"/>">Index</a></td>
    </tr>
    <tr>
        <td>Hello form</td>
        <td><a href="<c:url value="/hello-form"/>">Hello Form</a></td>
    </tr>
</table>

</html>
