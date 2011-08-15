<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Send ESB">

    <c:if test="${param.code != null}">
        <p class="message info">Message sent. Code = ${param.code}</p>
    </c:if>

    <c:if test="${param.error != null}">
        <p class="message error">Message sending failure. Error = ${param.error}</p>
    </c:if>


    <div class="form-block">
        <h3>ESB Message</h3>
        <form:form method="post" action="sendesb.do">
            <table>
                <tr>
                    <td><form:label path="serviceCategory">Service Category</form:label></td>
                    <td><form:input size="32" maxlength="64" path="serviceCategory" /></td>
                </tr>
                <tr>
                    <td><form:label path="serviceName">Service Name</form:label></td>
                    <td><form:input size="32" maxlength="64" path="serviceName" /></td>
                </tr>
                <tr>
                    <td><form:label path="message">Message</form:label></td>
                    <td><form:input size="32" maxlength="64" path="message" /></td>
                </tr>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Send ESB message"/>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</tag:pageWrapper>