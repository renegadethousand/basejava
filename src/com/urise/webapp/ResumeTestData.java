package com.urise.webapp;

import com.urise.webapp.model.AchievementInformation;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ExpirienceInformation;
import com.urise.webapp.model.GeneralInformation;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.ResumeSection;
import com.urise.webapp.model.SectionType;

import java.time.LocalDate;
import java.util.Map;

public class ResumeTestData {

    public static void main(String[] args) {
        Resume resume = new Resume("Ivan ivanov");

        GeneralInformation objective = new GeneralInformation();
        objective.setText("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям");
        resume.getResumeInfo().put(SectionType.OBJECTIVE, objective);

        GeneralInformation personal = new GeneralInformation();
        personal.setText("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры.");
        resume.getResumeInfo().put(SectionType.PERSONAL, personal);

        AchievementInformation achievement = new AchievementInformation();
        achievement.getText().add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievement.getText().add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievement.getText().add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке: Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей, интеграция CIFS/SMB java сервера.");
        achievement.getText().add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievement.getText().add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования и мониторинга системы по JMX (Jython/ Django).");
        achievement.getText().add("Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        resume.getResumeInfo().put(SectionType.ACHIEVEMENT, personal);

        AchievementInformation qualifications = new AchievementInformation();
        qualifications.getText().add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.getText().add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.getText().add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.getText().add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.getText().add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.getText().add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.getText().add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements).");
        qualifications.getText().add("Python: Django.");
        qualifications.getText().add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.getText().add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.getText().add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX, DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP, OAuth1, OAuth2, JWT.");
        qualifications.getText().add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.getText().add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios, iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.getText().add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования, архитектурных шаблонов, UML, функционального программирования");
        qualifications.getText().add("Родной русский, английский \"upper intermediate\"");
        resume.getResumeInfo().put(SectionType.QUALIFICATIONS, qualifications);

        ExpirienceInformation expirienceInformation = new ExpirienceInformation();

        ExpirienceInformation.Expiriense expiriense_1 = new ExpirienceInformation().new Expiriense();
        expiriense_1.setTitle("Java Online Projects");
        expiriense_1.setPosition("Автор проекта.");
        expiriense_1.setStartDate(LocalDate.of(2013,10,1));
        expiriense_1.setEndDate(LocalDate.of(1,1,1));
        expiriense_1.setText("Создание, организация и проведение Java онлайн проектов и стажировок.");
        expirienceInformation.getExpirienseList().add(expiriense_1);

        ExpirienceInformation.Expiriense expiriense_2 = new ExpirienceInformation().new Expiriense();
        expiriense_2.setTitle("Wrike");
        expiriense_2.setPosition("Старший разработчик (backend)");
        expiriense_2.setStartDate(LocalDate.of(2014,10,1));
        expiriense_2.setEndDate(LocalDate.of(2016,1,1));
        expiriense_2.setText("Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация, авторизация по OAuth1, OAuth2, JWT SSO.");
        expirienceInformation.getExpirienseList().add(expiriense_2);

        ExpirienceInformation.Expiriense expiriense_3 = new ExpirienceInformation().new Expiriense();
        expiriense_3.setTitle("RIT Center");
        expiriense_3.setPosition("Java архитектор");
        expiriense_3.setStartDate(LocalDate.of(2012,02,1));
        expiriense_3.setEndDate(LocalDate.of(2014,10,1));
        expiriense_3.setText("Организация процесса разработки системы ERP для разных окружений: релизная политика, версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway), конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной части системы. Разработка интергационных сервисов: CMIS, BPMN2, 1C (WebServices), сервисов общего назначения (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование из браузера документов MS Office. Maven + plugin development, Ant, Apache Commons, Spring security, Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote scripting via ssh tunnels, PL/Python");
        expirienceInformation.getExpirienseList().add(expiriense_3);

        ExpirienceInformation.Expiriense expiriense_4 = new ExpirienceInformation().new Expiriense();
        expiriense_4.setTitle("Luxoft (Deutsche Bank)");
        expiriense_4.setPosition("Ведущий программист");
        expiriense_4.setStartDate(LocalDate.of(2010,12,1));
        expiriense_4.setEndDate(LocalDate.of(2012,04,1));
        expiriense_4.setText("Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.");
        expirienceInformation.getExpirienseList().add(expiriense_4);

        ExpirienceInformation.Expiriense expiriense_5 = new ExpirienceInformation().new Expiriense();
        expiriense_5.setTitle("Yota");
        expiriense_5.setPosition("Ведущий специалист");
        expiriense_5.setStartDate(LocalDate.of(2008,06,1));
        expiriense_5.setEndDate(LocalDate.of(2010,12,1));
        expiriense_5.setText("Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента (Python/ Jython, Django, ExtJS)");
        expirienceInformation.getExpirienseList().add(expiriense_5);

        ExpirienceInformation.Expiriense expiriense_6 = new ExpirienceInformation().new Expiriense();
        expiriense_6.setTitle("Enkata");
        expiriense_6.setPosition("Разработчик ПО");
        expiriense_6.setStartDate(LocalDate.of(2007,03,1));
        expiriense_6.setEndDate(LocalDate.of(2008,06,1));
        expiriense_6.setText("Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS) частей кластерного J2EE приложения (OLAP, Data mining).");
        expirienceInformation.getExpirienseList().add(expiriense_6);

        ExpirienceInformation.Expiriense expiriense_7 = new ExpirienceInformation().new Expiriense();
        expiriense_7.setTitle("Siemens AG");
        expiriense_7.setPosition("Разработчик ПО");
        expiriense_7.setStartDate(LocalDate.of(2005,1,1));
        expiriense_7.setEndDate(LocalDate.of(2007,2,1));
        expiriense_7.setText("Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО на мобильной IN платформе Siemens @vantage (Java, Unix).");
        expirienceInformation.getExpirienseList().add(expiriense_7);

        ExpirienceInformation.Expiriense expiriense_8 = new ExpirienceInformation().new Expiriense();
        expiriense_8.setTitle("Alcatel");
        expiriense_8.setPosition("Ведущий программист");
        expiriense_8.setStartDate(LocalDate.of(1997,9,1));
        expiriense_8.setEndDate(LocalDate.of(2005,1,1));
        expiriense_8.setText("Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12 (CHILL, ASM).");
        expirienceInformation.getExpirienseList().add(expiriense_8);

        resume.getResumeInfo().put(SectionType.EXPERIENCE, expirienceInformation);

        ExpirienceInformation educationInformation = new ExpirienceInformation();

        ExpirienceInformation.Expiriense education_1 = new ExpirienceInformation().new Expiriense();
        education_1.setTitle("Coursera");
        education_1.setStartDate(LocalDate.of(2013,3,1));
        education_1.setEndDate(LocalDate.of(2013,5,1));
        education_1.setText("\t\"Functional Programming Principles in Scala\" by Martin Odersky");
        educationInformation.getExpirienseList().add(education_1);

        ExpirienceInformation.Expiriense education_2 = new ExpirienceInformation().new Expiriense();
        education_2.setTitle("Luxoft");
        education_2.setStartDate(LocalDate.of(2011,3,1));
        education_2.setEndDate(LocalDate.of(2011,4,1));
        education_2.setText("Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"");
        educationInformation.getExpirienseList().add(education_2);

        ExpirienceInformation.Expiriense education_3 = new ExpirienceInformation().new Expiriense();
        education_3.setTitle("Siemens AG");
        education_3.setStartDate(LocalDate.of(2005,1,1));
        education_3.setEndDate(LocalDate.of(2005,4,1));
        education_3.setText("3 месяца обучения мобильным IN сетям (Берлин)");
        educationInformation.getExpirienseList().add(education_3);

        ExpirienceInformation.Expiriense education_4 = new ExpirienceInformation().new Expiriense();
        education_4.setTitle("Alcatel");
        education_4.setStartDate(LocalDate.of(1997,9,1));
        education_4.setEndDate(LocalDate.of(1998,3,1));
        education_4.setText("6 месяцев обучения цифровым телефонным сетям (Москва)");
        educationInformation.getExpirienseList().add(education_4);

        ExpirienceInformation.Expiriense education_5 = new ExpirienceInformation().new Expiriense();
        education_5.setTitle("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
        education_5.setStartDate(LocalDate.of(1993,9,1));
        education_5.setEndDate(LocalDate.of(1996,7,1));
        education_5.setText("Аспирантура (программист С, С++)");
        educationInformation.getExpirienseList().add(education_5);

        ExpirienceInformation.Expiriense education_6 = new ExpirienceInformation().new Expiriense();
        education_6.setTitle("Санкт-Петербургский национальный исследовательский университет информационных технологий, механики и оптики");
        education_6.setStartDate(LocalDate.of(1987,9,1));
        education_6.setEndDate(LocalDate.of(1993,7,1));
        education_6.setText("Инженер (программист Fortran, C)");
        educationInformation.getExpirienseList().add(education_6);

        ExpirienceInformation.Expiriense education_7 = new ExpirienceInformation().new Expiriense();
        education_7.setTitle("Заочная физико-техническая школа при МФТИ");
        education_7.setStartDate(LocalDate.of(1984,9,1));
        education_7.setEndDate(LocalDate.of(1987,6,1));
        education_7.setText("Закончил с отличием");
        educationInformation.getExpirienseList().add(education_7);

        resume.getResumeInfo().put(SectionType.EDUCATION, educationInformation);

        resume.getContacts().put(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.getContacts().put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        for (Map.Entry<SectionType, ResumeSection> resumeSection : resume.getResumeInfo().entrySet()) {
            System.out.println(resumeSection.getKey().getTitle());
            resumeSection.getValue().print();
            System.out.println();
        }

        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle());
            System.out.println(contact.getValue());
            System.out.println();
        }
    }
}
