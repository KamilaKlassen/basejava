<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.basejava.webapp.model.Resume" scope="request"/>
    <title>Резюме ${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>

        <%--Contacts--%>
        <h3>Контакты:</h3>
        <c:forEach var="type" items="<%=ContactType.values()%>">
            <dl>
                <dt>${type.title}</dt>
                <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
            </dl>
        </c:forEach>


        <%--Sections--%>
        <h3>Секции:</h3>
        <c:forEach var="typeSection" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(typeSection)}"/>
            <jsp:useBean id="section"
                         type="com.basejava.webapp.model.AbstractSection"/>
            <dl>
                <dt>${typeSection.title}</dt>
                <dd>
                    <c:choose>
                        <c:when test="${typeSection=='PERSONAL' || typeSection=='OBJECTIVE'}">
                            <textarea name='${typeSection}' cols=100 rows=3><%=section%></textarea><br>
                        </c:when>
                        <c:when test="${typeSection=='QUALIFICATIONS' || typeSection=='ACHIEVEMENT'}">
                            <textarea name='${typeSection}' cols=100
                                      rows=5><%=String.join("\n", ((ListSection) section).getList())%></textarea><br>
                        </c:when>
                    </c:choose>
                </dd>
            </dl>
        </c:forEach>
        <hr>

        <%--Buttons--%>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>