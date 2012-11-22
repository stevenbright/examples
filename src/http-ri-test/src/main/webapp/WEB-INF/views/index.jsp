<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Main">

    <hr/>
    <h3>Get Hello</h3>
    <form method="POST" action="<c:url value="/invoke/get-hello.do"/>">
        <table class="form-contents">
            <tr>
                <td><label for="subject">Subject</label></td>
                <td>
                    <input type="text" size="32" maxlength="32" id="subject" name="subject" value="Anonymous"/>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" value="Invoke"/>
                </td>
            </tr>
        </table>
    </form>

    <hr/>
    <h3>Create Greeting</h3>
    <form method="POST" action="<c:url value="/invoke/create-greeting.do"/>">
        <table class="form-contents">
            <tr>
                <td><label for="origin">Origin</label></td>
                <td>
                    <input type="text" size="32" maxlength="32" id="origin" name="origin" value="Anonymous"/>
                </td>
            </tr>

            <tr>
                <td><label for="warm-level">Warm Level</label></td>
                <td>
                    <input type="text" size="32" maxlength="32" id="warm-level" name="warm-level" value="1"/>
                </td>
            </tr>

            <tr>
                <td><label for="sincerity">Sincerity</label></td>
                <td>
                    <input type="text" size="32" maxlength="32" id="sincerity" name="sincerity" value="0.75"/>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <input type="submit" value="Invoke"/>
                </td>
            </tr>
        </table>
    </form>

    <hr/>
    <p>Greeting service target: ${greetingServiceTarget}</p>
</tag:pageWrapper>