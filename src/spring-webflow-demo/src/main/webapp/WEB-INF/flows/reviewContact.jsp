<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Contacts Flow</title>
</head>
<body>


<h2>Enter Contact Details</h2>

<form:form method="post" modelAttribute="helloWorldForm">
    <input type="submit" name="_eventId_confirm" value="Confirm" />
    <input type="submit" name="_eventId_revise" value="Revise" />
    <input type="submit" name="_eventId_cancel" value="Cancel" />
</form:form>
</body>
</html>