<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <jsp:include page="/WEB-INF/tiles/page-header.jsp"/>
</head>

<h2>Hello Form</h2>

<div class="form-block">
    <form:form method="post" action="hello-form">
        <table>
            <tr>
                <td><form:label path="title">Title</form:label></td>
                <td><form:input size="16" maxlength="32" path="title" /></td>
            </tr>
            <tr>
                <td><form:label path="username">Username</form:label></td>
                <td><form:input size="16" maxlength="32" path="username" /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Say hello"/>
                </td>
            </tr>
        </table>
    </form:form>
</div>

</html>
