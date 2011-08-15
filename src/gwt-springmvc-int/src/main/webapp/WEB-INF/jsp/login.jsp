<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<body>

<div>
    <h1>Please Log In to Your Account</h1>
    <p>Please use the form below to log in to your account.</p>
</div>

<form action="loginProcess" method="post">
    <label for="j_username">Login</label>:
    <input id="j_username" name="j_username" size="20" maxlength="64" type="text"/>
    <br />
    <label for="j_password">Password</label>:
    <input id="j_password" name="j_password" size="20" maxlength="64" type="password"/>
    <br />
    <input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true"/>
    <label for="_spring_security_remember_me">Remember Me?</label>
    <br />
    <input type="submit" value="Login"/>
</form>

</body>
</html>
