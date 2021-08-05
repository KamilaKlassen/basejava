<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список всех резюме</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <h4><a href="resume?action=add" class="add">Добавить новое резюме</a></h4><br>
    <table>
        <tr>
            <th>Имя</th>
            <th colspan="2">Контактные данные</th>
            <th>Удалить резюме</th>
            <th>Редактировать резюме</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?uuid=${resume.uuid}&action=view">${resume.fullName}</a></td>
                <td><%=ContactType.MAIL.toHtml(resume.getContact(ContactType.MAIL))%>
                </td>
                <td><%=ContactType.MOBILE.toHtml(resume.getContact(ContactType.MOBILE))%>
                </td>
                <td align="center"><a href="resume?uuid=${resume.uuid}&action=delete"><img src="img/cancel.png" width="35"></a></td>
                <td align="center"><a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit-button.png" width="35"></a></td>
            </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
