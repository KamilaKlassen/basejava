<%@ page import="com.basejava.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.basejava.webapp.model.ContactType" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Список всех резюме</title>
    <style>
        table, th, td {
            border: 1px solid black;
            border-collapse: collapse;
            font-family: "Gill Sans", sans-serif;
        }

        th, td {
            padding: 5px;
        }

        th, td:hover {
            background-color: lightblue;
        }
    </style>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <table>
        <tr>
            <th>Имя</th>
            <th>Email</th>
        </tr>
        <%
            for (Resume resume : (List<Resume>) request.getAttribute("resumes")) {
        %>
        <tr>
            <td><a href="resume?uuid=<%=resume.getUuid()%>"><%=resume.getFullName()%>
            </a>
            </td>
            <td><%=resume.getContact(ContactType.MAIL)%>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
