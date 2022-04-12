<%@ page import="com.urise.webapp.model.ContactType" %>
<%@ page import="com.urise.webapp.model.SectionType" %>
<%@ page import="com.urise.webapp.model.ListSection" %>
<%@ page import="com.urise.webapp.model.OrganizationSection" %>
<%@ page import="com.urise.webapp.util.DateUtil" %><%--
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
        <c:forEach var="type" items="<%=SectionType.values()%>">
            <c:set var="section" value="${resume.getSection(type)}"/>
            <jsp:useBean id="section" type="com.urise.webapp.model.Section"/>
            <h3><a>${type.title}</a></h3>
            <c:choose>
                <c:when test="${type=='OBJECTIVE'}">
                    <input type='text' name='${type}' size=75 value=<%=section%>>
                </c:when>
                <c:when test="${type=='PERSONAL'}">
                    <textarea name='${type}' cols=75 rows=5><%=section%></textarea>
                </c:when>
                <c:when test="${type=='QUALIFICATIONS' || type=='ACHIEVEMENT'}">
                    <textarea name='${type}' cols=75
                              rows=5><%=String.join("\n", ((ListSection) section).getItems())%>
                    </textarea>
                </c:when>
                <c:when test="${type=='EXPERIENCE' || type=='EDUCATION'}">
                    <c:forEach var="org" items="<%=((OrganizationSection) section).getOrganizationsList()%>"
                               varStatus="counter">
                        <dl>
                            <dt>Название учереждения:</dt>
                            <dd><input type="text" name="${type}" size=100 value="${org.homePage.name}"></dd>
                        </dl>
                        <dl>
                            <dt>Сайт учереждения:</dt>
                            <dd><input type="text" name="${type}_url" size=100 value="${org.homePage.url}"></dd>
                        </dl>
                        <br>
                        <div style="margin-left: 30px">
                            <c:forEach var="pos" items="${org.positions}">
                                <jsp:useBean id="pos" type="com.urise.webapp.model.Organization.Position"/>
                                <dl>
                                    <dt>Начальная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}startDate" size=10
                                               value="<%=DateUtil.format(pos.getStartDate())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Конечная дата:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}endDate" size=10
                                               value="<%=DateUtil.format(pos.getStartDate())%>" placeholder="MM/yyyy">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Должность:</dt>
                                    <dd>
                                        <input type="text" name="${type}${counter.index}title" size=75
                                               value="${pos.title}">
                                    </dd>
                                </dl>
                                <dl>
                                    <dt>Описание:</dt>
                                    <dd>
                                        <textarea name="${type}${counter.index}description" rows=2
                                                  cols=75>${pos.description}</textarea>
                                    </dd>
                                </dl>
                            </c:forEach>
                        </div>
                    </c:forEach>
                </c:when>
            </c:choose>
        </c:forEach>
        <button type="submit">Сохранить</button>
        <button type="reset" onclick="window.history.back()">Отменить</button>
<%--        <h3>Секции:</h3>--%>
<%--        <c:forEach var="sectionType" items="<%=SectionType.values()%>">--%>
<%--            <c:choose>--%>
<%--                <c:when test="${sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION}">--%>
<%--                    <dl>--%>
<%--                        <dt>${sectionType.title}</dt>--%>
<%--                    </dl>--%>
<%--                    </br>--%>
<%--                    <c:set var="currentSection" value="${resume.getSection(sectionType)}"/>--%>
<%--                    <jsp:useBean id="currentSection" type="com.urise.webapp.model.OrganizationSection"/>--%>
<%--                    <c:set var="organizationList" value="<%=currentSection.getOrganizationsList()%>"/>--%>
<%--                    <input type="hidden" name="${sectionType.name()}_organizations" size="80" value="${organizationList.size() + 1}">--%>
<%--                    <c:forEach var="organization" items="${organizationList}" varStatus="counter">--%>
<%--                        <dl>--%>
<%--                            <dt>Название</dt>--%>
<%--                            <dd><input type="text" name="${sectionType.name()}_name_${counter.index}" size="80" value="${organization.homePage.name}"></dd>--%>
<%--                        </dl>--%>
<%--                        <dl>--%>
<%--                            <dt>Ссылка</dt>--%>
<%--                            <dd><input type="text" name="${sectionType.name()}_page_${counter.index}" size="80" value="${organization.homePage.url}"></dd>--%>
<%--                        </dl>--%>
<%--                        <input type="hidden" name="${sectionType.name()}_${counter.index}_positions" size="80" value="${organization.positions.size() + 1}">--%>
<%--                        <c:forEach var="position" items="${organization.positions}" varStatus="expCounter">--%>
<%--                            <dl>--%>
<%--                                <dt>Дата начала</dt>--%>
<%--                                <dd><input type="text" name="${sectionType.name()}_startDate_${counter.index}_${expCounter.index}" size="80" value="${position.startDate}"></dd>--%>
<%--                            </dl>--%>
<%--                            <dl>--%>
<%--                                <dt>Дата окончания</dt>--%>
<%--                                <dd><input type="text" name="${sectionType.name()}_endDate_${counter.index}_${expCounter.index}" size="80" value="${position.endDate}"></dd>--%>
<%--                            </dl>--%>
<%--                            <dl>--%>
<%--                                <dt>Должность</dt>--%>
<%--                                <dd><input type="text" name="${sectionType.name()}_title_${counter.index}_${expCounter.index}" size="80" value="${position.title}"></dd>--%>
<%--                            </dl>--%>
<%--                            <dl>--%>
<%--                                <dt>Описание</dt>--%>
<%--                                <dd><input type="text" name="${sectionType.name()}_description_${counter.index}_${expCounter.index}" size="80" value="${position.description}"></dd>--%>
<%--                            </dl>--%>
<%--                        </c:forEach>--%>
<%--                        <dl>--%>
<%--                            <dt>Добавить новую позицию в организацию ${organization.homePage.name}</dt>--%>
<%--                        </dl>--%>
<%--                        <dl>--%>
<%--                            <dt>Дата начала</dt>--%>
<%--                            <dd><input type="text" name="${sectionType.name()}_startDate_${counter.index}_${organization.positions.size()}" size="80" value="${position.startDate}"></dd>--%>
<%--                        </dl>--%>
<%--                        <dl>--%>
<%--                            <dt>Дата окончания</dt>--%>
<%--                            <dd><input type="text" name="${sectionType.name()}_endDate_${counter.index}_${organization.positions.size()}" size="80" value="${position.endDate}"></dd>--%>
<%--                        </dl>--%>
<%--                        <dl>--%>
<%--                            <dt>Должность</dt>--%>
<%--                            <dd><input type="text" name="${sectionType.name()}_title_${counter.index}_${organization.positions.size()}" size="80" value="${position.title}"></dd>--%>
<%--                        </dl>--%>
<%--                        <dl>--%>
<%--                            <dt>Описание</dt>--%>
<%--                            <dd><input type="text" name="${sectionType.name()}_description_${counter.index}_${organization.positions.size()}" size="80" value="${position.description}"></dd>--%>
<%--                        </dl>--%>
<%--                        </br>--%>
<%--                    </c:forEach>--%>
<%--                    <dl>--%>
<%--                        <dt>Добавить ${sectionType.title}</dt>--%>
<%--                    </dl>--%>
<%--                    <dl>--%>
<%--                        <dt>Название</dt>--%>
<%--                        <dd><input type="text" name="${sectionType.name()}_name_${organizationList.size()}" size="80" value=""></dd>--%>
<%--                    </dl>--%>
<%--                    <dl>--%>
<%--                        <dt>Ссылка</dt>--%>
<%--                        <dd><input type="text" name="${sectionType.name()}_page_${organizationList.size()}" size="80" value=""></dd>--%>
<%--                    </dl>--%>
<%--                    <input type="hidden" name="${sectionType.name()}_${organizationList.size()}_positions" size="80" value="1">--%>
<%--                    <dl>--%>
<%--                        <dt>Дата начала</dt>--%>
<%--                        <dd><input type="text" name="${sectionType.name()}_startDate_${organizationList.size()}_0" size="80" value="${position.startDate}"></dd>--%>
<%--                    </dl>--%>
<%--                    <dl>--%>
<%--                        <dt>Дата окончания</dt>--%>
<%--                        <dd><input type="text" name="${sectionType.name()}_endDate_${organizationList.size()}_0" size="80" value="${position.endDate}"></dd>--%>
<%--                    </dl>--%>
<%--                    <dl>--%>
<%--                        <dt>Должность</dt>--%>
<%--                        <dd><input type="text" name="${sectionType.name()}_title_${organizationList.size()}_0" size="80" value="${position.title}"></dd>--%>
<%--                    </dl>--%>
<%--                    <dl>--%>
<%--                        <dt>Описание</dt>--%>
<%--                        <dd><input type="text" name="${sectionType.name()}_description_${organizationList.size()}_0" size="80" value="${position.description}"></dd>--%>
<%--                    </dl>--%>
<%--                </c:when>--%>
<%--                <c:otherwise>--%>
<%--                    <dl>--%>
<%--                        <dt>${sectionType.title}</dt>--%>
<%--                        <p><textarea rows="3" cols="45" name="${sectionType.name()}">${resume.getSection(sectionType)}</textarea></p>--%>
<%--                    </dl>--%>
<%--                </c:otherwise>--%>
<%--            </c:choose>--%>
<%--        </c:forEach>--%>
<%--        <hr>--%>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"></jsp:include>
</body>
</html>
