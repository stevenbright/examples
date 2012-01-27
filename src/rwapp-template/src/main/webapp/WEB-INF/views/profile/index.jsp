<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Profile">
    <p>Profile</p>
    <table>
        <tr>
            <td>Id:</td>
            <td>${profile.id}</td>
        </tr>
    </table>
</tag:pageWrapper>