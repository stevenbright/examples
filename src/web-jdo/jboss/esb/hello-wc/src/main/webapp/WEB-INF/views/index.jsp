<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Main">
    <p>Hello there! Greeting data is as follows:</p>
    <table>
        <tr>
            <td>Origin</td>
            <td>${hello.origin}</td>
        </tr>
        <tr>
            <td>Greeting</td>
            <td>${hello.greeting}</td>
        </tr>
        <tr>
            <td>Created</td>
            <td>${hello.created}</td>
        </tr>
    </table>

    <h3>Commands</h3>
    <ul>
        <li><a href="<c:url value="/sendesb.html" />">Send ESB message</a></li>
        <li><a href="<c:url value="/sendjms.html" />">Send JMS message</a></li>
    </ul>
</tag:pageWrapper>