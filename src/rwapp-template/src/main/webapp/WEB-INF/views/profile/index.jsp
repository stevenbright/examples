<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Profile">
    <table class="std">
        <tr>
            <td><strong>Profile Object</strong></td>
            <td>${profile}</td>
        </tr>
    </table>
</tag:pageWrapper>