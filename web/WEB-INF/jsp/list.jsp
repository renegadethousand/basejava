<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.urise.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page import="com.urise.webapp.model.ContactType" %><%--
  Created by IntelliJ IDEA.
  User: Джабраил
  Date: 28.03.2022
  Time: 23:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>Список всех резюме</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"></jsp:include>
<section>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Имя</th>
            <th>Email</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.urise.webapp.model.Resume"></jsp:useBean>
        <tr>
            <td><a href="resume?uuid=${resume.uuid}">${resume.fullName}</a></td>
            <td>${resume.getContact(ContactType.MAIL)}</td>
        </tr>
        </c:forEach>
    </table>
</section>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>
