<%@ page import="com.basejava.webapp.model.ListSection" %>
<%@ page import="com.basejava.webapp.model.OrganizationSection" %>
<%@ page import="com.basejava.webapp.util.HtmlUtil" %>
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

<%--Contacts--%>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit-button.png"
                                                                                      width="35"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br>
        </c:forEach>
    <p>
</section>


<%--Default photo--%>
<div id="photo">
    <img id="output" src="img/no-photo.png" width="120" height="auto">
</div>


<%--Sections--%>
<section>
    <p>
        <c:forEach var="sectionEntry" items="${resume.sections}">
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.SectionType, com.basejava.webapp.model.AbstractSection>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.basejava.webapp.model.AbstractSection"/>
    <hr>
    <h3>${type.title}</h3><br>
    <c:choose>

        <%--Personal--%>
        <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
            <h4 id="personal"><%=section%>
            </h4>
            <br>
        </c:when>

        <%--Qualifications--%>
        <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">

            <c:forEach var="item" items="<%=((ListSection) section).getList()%>">
                <li>${item}</li>
            </c:forEach>
        </c:when>

        <%--Organisations--%>
        <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
            <table class="org">
                <c:forEach var="org" items="<%=((OrganizationSection) section).getExperienceList()%>">
                    <tr class="org">
                        <td colspan="2" class="org">
                            <c:choose>
                                <c:when test="${empty org.link.url}">
                                    <h3>${org.link.name}</h3>
                                </c:when>
                                <c:otherwise>
                                    <h3><a href="${org.link.url}">${org.link.name}</a></h3>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    <c:forEach var="position" items="${org.positionList}">
                        <jsp:useBean id="position" type="com.basejava.webapp.model.Experience.Position"/>
                        <tr class="org">
                            <td width="15%" style="vertical-align: top" class="org"><%=HtmlUtil.formatDates(position)%>
                            </td>
                            <td class="org"><b>${position.title}</b><br>${position.description}</td>
                        </tr>
                    </c:forEach>
                </c:forEach>
            </table>
        </c:when>
    </c:choose>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>