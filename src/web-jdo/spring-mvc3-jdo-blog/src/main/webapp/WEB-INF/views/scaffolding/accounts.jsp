<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>JDO Blog &raquo; Scaffolding &raquo; Accounts</title>
</head>
<body>
<h1>JDO Blog &raquo; Scaffolding &raquo; Accounts</h1>
<p>Message is: ${message}</p>

<table>
    <thead>
    <tr>
        <th scope="col">Id</th>
        <th scope="col">Display Name</th>
        <th scope="col">Avatar Url</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach var="account" items="${accounts}">
        <tr>
          <td>${account.id}</td>
          <td>${account.displayName}</td>
          <td>${account.avatarUrl}</td>
        </tr>
      </c:forEach>
    </tbody>
</table>
</body>
</html>