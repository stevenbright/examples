<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Add person</title>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">

    <link rel="stylesheet" type="text/css" href="static/css/style.css" />
    <script type="text/javascript" src="static/js/jquery.min.js"></script>

    <script type="text/javascript">
        $(document).ready(function() {
        })
    </script>
</head>

<body>
<h2>Sample Spring + Spring MVC + Hibernate + JPA Web App</h2>
<h3>Add person</h3>

<p>Note: all the non-ASCII-formed posts won't be handled correctly.</p>

<f:form>
    <table>
        <!-- Name Row -->
        <tr>
            <td>Name</td>
            <td><f:input path="name"/></td>
        </tr>
        <!-- Age Row -->
        <tr>
            <td>Age</td>
            <td><f:input path="age"/></td>
        </tr>
        <!-- Subscribed To Posts Row -->
        <tr>
            <td>Subscribed To Posts</td>
            <td><f:input path="subscribedToPosts"/></td>
        </tr>
        <!-- Status Row -->
        <tr>
            <td>Status</td>
            <td><f:input path="status"/></td>
        </tr>

        <!-- Submit button -->
        <tr>
           <td colspan="2" align="right">
               <input type="submit" value="ok">
           </td>
        </tr>
    </table>
</f:form>

</body>
</html>
