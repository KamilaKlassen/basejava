<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page import="com.basejava.webapp.model.SectionType" %>
<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.OrganizationSection" %>
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
            <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
            <jsp:useBean id="typeSection" type="com.basejava.webapp.model.SectionType"/>

            <dl>
                <dt class="bold">${typeSection.title}</dt>
                <dd>
                    <c:choose>

                        <%--Personal--%>
                        <c:when test="${typeSection=='PERSONAL' || typeSection=='OBJECTIVE'}">
                            <textarea name='${typeSection}' cols=100 rows=3><%=section%></textarea><br>
                        </c:when>

                        <%--Qualifications--%>
                        <c:when test="${typeSection=='QUALIFICATIONS' || typeSection=='ACHIEVEMENT'}">
                            <textarea name='${typeSection}' cols=100
                                      rows=5><%=String.join("\n", ((ListSection) section).getList())%></textarea><br>
                        </c:when>


                        <%--Organisations--%>
                        <c:when test="${typeSection=='EXPERIENCE' || typeSection=='EDUCATION'}">
                            <c:forEach var="org" items="<%=((OrganizationSection)section).getExperienceList()%>">
                                <dl>
                                    <dt>Название организации:</dt>
                                    <dd><input type="text" name='${typeSection}' size=75 value="${org.link.name}"></dd>
                                </dl>
                                <dl>
                                    <dt>Сайт организации:</dt>
                                    <dd><input type="text" name="${typeSection}" size=75 value="${org.link.url}"></dd>
                                </dl>

                                <%--Positions--%>
                                <c:forEach var="pos" items="${org.positionList}">
                                    <jsp:useBean id="pos" type="com.basejava.webapp.model.Experience.Position"/>
                                    <dl>
                                        <dt>Дата начала работы:</dt>
                                        <dd>
                                            <input type="date" name="${typeSection}" size=10
                                                   value="<%=pos.getStartDate()%>">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Дата окончания работы:</dt>
                                        <dd>
                                            <input type="date" name="${typeSection}" size=10
                                                   value="<%=pos.getEndDate()%>"></dd>
                                    </dl>
                                    <dl>
                                        <dt>Должность в организации:</dt>
                                        <dd><input type="text" name=${typeSection}" size=75
                                                   value="${pos.title}">
                                        </dd>
                                    </dl>
                                    <dl>
                                        <dt>Описание должности:</dt>
                                        <dd><textarea name="${typeSection}" rows=5
                                                      cols=75>${pos.description}</textarea></dd>
                                    </dl>
                                    <br>
                                </c:forEach>
                                <hr>
                            </c:forEach>
                        </c:when>
                    </c:choose>
                </dd>
            </dl>
        </c:forEach>

        <%--Buttons--%>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>