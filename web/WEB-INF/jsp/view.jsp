<%@ page import="com.urise.webapp.model.TextSection" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.HtmlUtil" %><%--
  Created by IntelliJ IDEA.
  User: Джабраил
  Date: 29.03.2022
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <jsp:useBean id="resume" type="com.urise.webapp.model.Resume" scope="request"></jsp:useBean>
    <title>${resume.fullName}</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"></jsp:include>
<section>
    <h2>${resume.fullName}&nbsp;<a href="resume?uuid=${resume.uuid}&action=edit"><img src="img/pencil.png"></a></h2>
    <p>
        <c:forEach var="contactEntry" items="${resume.contacts}">
            <jsp:useBean id="contactEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.ContactType, java.lang.String>"/>
            <%=contactEntry.getKey().toHtml(contactEntry.getValue())%><br/>
        </c:forEach>
    </p>
    <hr>
    <table cellpadding="2">
        <c:forEach var="sectionEntry" items="${resume.sections}">--%>
            <jsp:useBean id="sectionEntry"
                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>
            <c:set var="type" value="${sectionEntry.key}"/>
            <c:set var="section" value="${sectionEntry.value}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>

            <tr>
                <td><h3><a name="type.name">${type.title}</a></h3></td>
                    <c:if test="${type=='OBJECTIVE'}">
                        <td>
                            <h3><%=((TextSection) section).getContent()%></h3>
                        </td>
                    </c:if>
            </tr>
            <c:if test="${type != 'OBJECTIVE'}">
                <c:choose>
                    <c:when test="${type=='PERSONAL'}">
                        <tr>
                            <td>
                                <%=((TextSection) section).getContent()%>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                        <tr>
                            <td>
                                <ul>
                                    <c:forEach var="item" items="<%=((ListSection) section).getItems()%>">
                                        <li>${item}</li>
                                    </c:forEach>
                                </ul>
                            </td>
                        </tr>
                    </c:when>
                    <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                        <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizationsList()%>">
                            <tr>
                                <td>
                                    <c:choose>
                                        <c:when test="${empty org.homePage.url}">
                                            <h3>${org.homePage.name}</h3>
                                        </c:when>
                                        <c:otherwise>
                                            <h3><a href="${org.homePage.url}">${org.homePage.name}</a></h3>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <c:forEach var="postion" items="${org.positions}">
                                <jsp:useBean id="postion" type="com.urise.webapp.model.Organization.Position">
                                    <tr>
                                        <td><%=HtmlUtil.formatDates(postion)%></td>
                                        <td>
                                            <b>${postion.title}</b>
                                            <br>
                                            ${position.description}
                                        </td>
                                    </tr>
                                </jsp:useBean>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </c:if>
        </c:forEach>
    </table>
    <button onclick="window.history.back()">OK</button>
<%--    <p>--%>
<%--        <c:forEach var="sectionEntry" items="${resume.sections}">--%>
<%--            <jsp:useBean id="sectionEntry"--%>
<%--                         type="java.util.Map.Entry<com.urise.webapp.model.SectionType, com.urise.webapp.model.Section>"/>--%>
<%--            <%=sectionEntry.getKey().toHtml(sectionEntry.getValue())%><br/>--%>
<%--        </c:forEach>--%>
<%--    </p>--%>
</section>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>
