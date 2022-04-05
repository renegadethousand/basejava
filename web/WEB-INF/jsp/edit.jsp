<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %><%--
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
    <form method="post" action="resume" enctype="application/x-www-form-urlencoded">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <dl>
            <dt>Имя:</dt>
            <dd><input type="text" required name="fullName" size=50 value="${resume.fullName}"></dd>
        </dl>
        <h3>Контакты:</h3>
        <p>
        <c:forEach var="type" items="<%=ContactType.values()%>">
        <dl>
            <dt>${type.title}</dt>
            <dd><input type="text" name="${type.name()}" size=30 value="${resume.getContact(type)}"></dd>
        </dl>
        </c:forEach>
        </p>
        <h3>Секции:</h3>
        <c:forEach var="sectionType" items="<%=SectionType.values()%>">
            <c:choose>
                <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">
                    <dl>
                        <dt>${sectionType.title}</dt>
                    </dl>
                    </br>
                    <c:set var="currentSection" value="${resume.getSection(sectionType)}"/>
                    <jsp:useBean id="currentSection" type="com.urise.webapp.model.OrganizationSection"/>
                    <c:set var="organizationList" value="<%=currentSection.getOrganizationsList()%>"/>
                    <input type="hidden" name="${sectionType.name()}_organizations" size="80" value="${organizationList.size() + 1}">
                    <c:forEach var="organization" items="${organizationList}" varStatus="counter">
                        <dl>
                            <dt>Название</dt>
                            <dd><input type="text" name="${sectionType.name()}_name_${counter.index}" size="80" value="${organization.homePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Ссылка</dt>
                            <dd><input type="text" name="${sectionType.name()}_page_${counter.index}" size="80" value="${organization.homePage.url}"></dd>
                        </dl>
                        <input type="hidden" name="${sectionType.name()}_${counter.index}_positions" size="80" value="${organization.positions.size() + 1}">
                        <c:forEach var="position" items="${organization.positions}" varStatus="expCounter">
                            <dl>
                                <dt>Дата начала</dt>
                                <dd><input type="text" name="${sectionType.name()}_startDate_${counter.index}_${expCounter.index}" size="80" value="${position.startDate}"></dd>
                            </dl>
                            <dl>
                                <dt>Дата окончания</dt>
                                <dd><input type="text" name="${sectionType.name()}_endDate_${counter.index}_${expCounter.index}" size="80" value="${position.endDate}"></dd>
                            </dl>
                            <dl>
                                <dt>Должность</dt>
                                <dd><input type="text" name="${sectionType.name()}_title_${counter.index}_${expCounter.index}" size="80" value="${position.title}"></dd>
                            </dl>
                            <dl>
                                <dt>Описание</dt>
                                <dd><input type="text" name="${sectionType.name()}_description_${counter.index}_${expCounter.index}" size="80" value="${position.description}"></dd>
                            </dl>
                        </c:forEach>
                        <dl>
                            <dt>Добавить новую позицию в организацию ${organization.homePage.name}</dt>
                        </dl>
                        <dl>
                            <dt>Дата начала</dt>
                            <dd><input type="text" name="${sectionType.name()}_startDate_${counter.index}_${organization.positions.size()}" size="80" value="${position.startDate}"></dd>
                        </dl>
                        <dl>
                            <dt>Дата окончания</dt>
                            <dd><input type="text" name="${sectionType.name()}_endDate_${counter.index}_${organization.positions.size()}" size="80" value="${position.endDate}"></dd>
                        </dl>
                        <dl>
                            <dt>Должность</dt>
                            <dd><input type="text" name="${sectionType.name()}_title_${counter.index}_${organization.positions.size()}" size="80" value="${position.title}"></dd>
                        </dl>
                        <dl>
                            <dt>Описание</dt>
                            <dd><input type="text" name="${sectionType.name()}_description_${counter.index}_${organization.positions.size()}" size="80" value="${position.description}"></dd>
                        </dl>
                        </br>
                    </c:forEach>
                    <dl>
                        <dt>Добавить ${sectionType.title}</dt>
                    </dl>
                    <dl>
                        <dt>Название</dt>
                        <dd><input type="text" name="${sectionType.name()}_name_${organizationList.size()}" size="80" value=""></dd>
                    </dl>
                    <dl>
                        <dt>Ссылка</dt>
                        <dd><input type="text" name="${sectionType.name()}_page_${organizationList.size()}" size="80" value=""></dd>
                    </dl>
                    <input type="hidden" name="${sectionType.name()}_${organizationList.size()}_positions" size="80" value="1">
                    <dl>
                        <dt>Дата начала</dt>
                        <dd><input type="text" name="${sectionType.name()}_startDate_${organizationList.size()}_0" size="80" value="${position.startDate}"></dd>
                    </dl>
                    <dl>
                        <dt>Дата окончания</dt>
                        <dd><input type="text" name="${sectionType.name()}_endDate_${organizationList.size()}_0" size="80" value="${position.endDate}"></dd>
                    </dl>
                    <dl>
                        <dt>Должность</dt>
                        <dd><input type="text" name="${sectionType.name()}_title_${organizationList.size()}_0" size="80" value="${position.title}"></dd>
                    </dl>
                    <dl>
                        <dt>Описание</dt>
                        <dd><input type="text" name="${sectionType.name()}_description_${organizationList.size()}_0" size="80" value="${position.description}"></dd>
                    </dl>
                </c:when>
                <c:otherwise>
                    <dl>
                        <dt>${sectionType.title}</dt>
                        <p><textarea rows="3" cols="45" name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea></p>
                    </dl>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <hr>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>
