<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@ page pageEncoding="UTF-8" contentType="text/html;charset=UTF-8" language="java" %>

<tag:pageWrapper sectionName="Upload">
    <p>Upload a file:</p>

    <c:if test="${not empty param.filenum}">
        <p class="message info">File uploaded, filenum = ${param.filenum}</p>
    </c:if>

    <c:if test="${not empty param.error}">
        <p class="message error">Upload failed</p>
    </c:if>


    <form action="<c:url value="/upload/upload.do" />" method="POST" enctype="multipart/form-data">
        <table>
            <tr>
                <td>Select file</td>
                <td><input type="file" name="uploaded-file" value="..." /></td>
            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Upload!"/>
                </td>
            </tr>
        </table>
    </form>
</tag:pageWrapper>