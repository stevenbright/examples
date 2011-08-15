<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="navigation">
    <table>
        <tr>
            <td colspan="3"><h3>Navigation</h3></td>
            <p></p>
        </tr>
        <tr>
            <!-- c:url is the same as pageContext.request.contextPath -->
            <td><a href="<c:url value="/hello" />">Hello</a></td>

            <td><a href="${pageContext.request.contextPath}/contacts">Contacts</a></td>
            <td><a href="${pageContext.request.contextPath}/contact-flow">Contact Flow</a></td>
        </tr>
    </table>
</div>
