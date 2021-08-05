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

<%--Contacts--%>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/edit-button.png"
                                                                                      width="35"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.basejava.webapp.model.ContactType, java.lang.String>"/>
                <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
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
    <h3>${type.title}</h3><br>
    <c:choose>
        <c:when test="${type=='PERSONAL' || type=='OBJECTIVE'}">
            <h4><%=section%>
            </h4>
            <br>
            <hr>
        </c:when>
        <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">

            <c:forEach var="item" items="<%=((ListSection) section).getList()%>">
                <li>${item}</li>
            </c:forEach>
            <hr>
        </c:when>
    </c:choose>
    </c:forEach>
    </p>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>