package com.urise.webapp;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.BulletedListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationSection;
import com.urise.webapp.model.Position;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.AbstractSection;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ResumeTestData {

    public static Resume generateResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        TextSection objective = new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и" +
                " Enterprise технологиям");
        resume.getSections().put(SectionType.OBJECTIVE, objective);

        TextSection personal = new TextSection("Аналитический склад ума, сильная логика, креативность," +
                " инициативность. Пурист кода и архитектуры.");
        resume.getSections().put(SectionType.PERSONAL, personal);

        List<String> achievements = new ArrayList<>();
        achievements.add("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"" +
                "Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное" +
                " взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.");
        achievements.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike." +
                " Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievements.add("Налаживание процесса разработки и непрерывной интеграции ERP системы River BPM. Интеграция" +
                " с 1С, Bonita BPM, CMIS, LDAP. Разработка приложения управления окружением на стеке:" +
                " Scala/Play/Anorm/JQuery. Разработка SSO аутентификации и авторизации различных ERP модулей," +
                " интеграция CIFS/SMB java сервера.");
        achievements.add("Реализация c нуля Rich Internet Application приложения на стеке технологий JPA," +
                " Spring, Spring-MVC, GWT, ExtGWT (GXT), Commet, HTML5, Highstock для алгоритмического трейдинга.");
        achievements.add("Создание JavaEE фреймворка для отказоустойчивого взаимодействия слабо-связанных сервисов" +
                " (SOA-base архитектура, JAX-WS, JMS, AS Glassfish). Сбор статистики сервисов и информации о" +
                " состоянии через систему мониторинга Nagios. Реализация онлайн клиента для администрирования" +
                " и мониторинга системы по JMX (Jython/ Django).");
        achievements.add("Реализация протоколов по приему платежей всех основных платежных системы России" +
                " (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");
        BulletedListSection achievement = new BulletedListSection(achievements);
        resume.getSections().put(SectionType.ACHIEVEMENT, personal);

        List<String> qualifications = new ArrayList<>();
        qualifications.add("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2");
        qualifications.add("Version control: Subversion, Git, Mercury, ClearCase, Perforce");
        qualifications.add("DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,");
        qualifications.add("MySQL, SQLite, MS SQL, HSQLDB");
        qualifications.add("Languages: Java, Scala, Python/Jython/PL-Python, JavaScript, Groovy,");
        qualifications.add("XML/XSD/XSLT, SQL, C/C++, Unix shell scripts,");
        qualifications.add("Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis," +
                " Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice," +
                " GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, Apache Commons, Eclipse SWT, JUnit," +
                " Selenium (htmlelements).");
        qualifications.add("Python: Django.");
        qualifications.add("JavaScript: jQuery, ExtJS, Bootstrap.js, underscore.js");
        qualifications.add("Scala: SBT, Play2, Specs2, Anorm, Spray, Akka");
        qualifications.add("Технологии: Servlet, JSP/JSTL, JAX-WS, REST, EJB, RMI, JMS, JavaMail, JAXB, StAX, SAX," +
                " DOM, XSLT, MDB, JMX, JDBC, JPA, JNDI, JAAS, SOAP, AJAX, Commet, HTML5, ESB, CMIS, BPMN2, LDAP," +
                " OAuth1, OAuth2, JWT.");
        qualifications.add("Инструменты: Maven + plugin development, Gradle, настройка Ngnix,");
        qualifications.add("администрирование Hudson/Jenkins, Ant + custom task, SoapUI, JPublisher, Flyway, Nagios," +
                " iReport, OpenCmis, Bonita, pgBouncer.");
        qualifications.add("Отличное знание и опыт применения концепций ООП, SOA, шаблонов проектрирования," +
                " архитектурных шаблонов, UML, функционального программирования");
        qualifications.add("Родной русский, английский \"upper intermediate\"");
        BulletedListSection qualification = new BulletedListSection(qualifications);
        resume.getSections().put(SectionType.QUALIFICATIONS, qualification);

        List<Organization> organizations = new ArrayList<>();
        Organization experience_1 = new Organization("Java Online Projects",
                "http://javaops.ru/",
                List.of(new Position(LocalDate.of(2013, 10, 1),
                        LocalDate.of(1, 1, 1),
                        "Автор проекта.",
                        "Создание, организация и проведение Java онлайн проектов и стажировок."))
        );
        organizations.add(experience_1);

        Organization experience_2 = new Organization("Wrike",
                "https://www.wrike.com/",
                List.of(new Position(LocalDate.of(2014, 10, 1),
                        LocalDate.of(2016, 1, 1),
                        "Старший разработчик (backend)",
                        "Проектирование и разработка онлайн платформы управления проектами Wrike (Java 8 API, \" +\n" +
                                "Maven, Spring, MyBatis, Guava, Vaadin, PostgreSQL, Redis). Двухфакторная аутентификация,\" +\n" +
                                "авторизация по OAuth1, OAuth2, JWT SSO.")));
        organizations.add(experience_2);

        Organization experience_3 = new Organization("RIT Center",
                null,
                List.of(new Position(LocalDate.of(2012, 2, 1),
                        LocalDate.of(2014, 1, 1),
                        "Java архитектор",
                        "Организация процесса разработки системы ERP для разных окружений: релизная политика," +
                                " версионирование, ведение CI (Jenkins), миграция базы (кастомизация Flyway)," +
                                " конфигурирование системы (pgBoucer, Nginx), AAA via SSO. Архитектура БД и серверной" +
                                " части системы. Разработка интергационных сервисов:" +
                                " CMIS, BPMN2, 1C (WebServices), сервисов общего назначения" +
                                " (почта, экспорт в pdf, doc, html). Интеграция Alfresco JLAN для online редактирование" +
                                " из браузера документов MS Office." +
                                " Maven + plugin development, Ant, Apache Commons, Spring security," +
                                " Spring MVC, Tomcat,WSO2, xcmis, OpenCmis, Bonita, Python scripting, Unix shell remote" +
                                " scripting via ssh tunnels, PL/Python")));
        organizations.add(experience_3);

        Organization experience_4 = new Organization("Luxoft (Deutsche Bank)",
                "http://www.luxoft.ru/",
                List.of(new Position(LocalDate.of(2010, 12, 1),
                        LocalDate.of(2012, 4, 1),
                        "Ведущий программист",
                        "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT," +
                                " GWT, Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения" +
                                " для администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга." +
                                " JPA, Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")));
        organizations.add(experience_4);

        Organization experience_5 = new Organization("Yota",
                "https://www.yota.ru/",
                List.of(new Position(LocalDate.of(2008, 6, 1),
                        LocalDate.of(2010, 12, 1),
                        "Ведущий специалист",
                        "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\"" +
                                " (GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2)." +
                                " Реализация администрирования, статистики и мониторинга фреймворка. Разработка online JMX клиента" +
                                " (Python/ Jython, Django, ExtJS)")));
        organizations.add(experience_5);

        Organization experience_6 = new Organization("Enkata",
                "http://enkata.com/",
                List.of(new Position(LocalDate.of(2007, 3, 1),
                        LocalDate.of(2008, 6, 1),
                        "Разработчик ПО",
                        "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS)" +
                                " частей кластерного J2EE приложения (OLAP, Data mining).")));
        organizations.add(experience_6);

        Organization experience_7 = new Organization("Siemens AG",
                "https://www.siemens.com/ru/ru/home.html",
                List.of(new Position(LocalDate.of(2005, 1, 1),
                        LocalDate.of(2007, 2, 1),
                        "Разработчик ПО",
                        "Разработка информационной модели, проектирование интерфейсов, реализация и отладка ПО" +
                                " на мобильной IN платформе Siemens @vantage (Java, Unix).")));
        organizations.add(experience_7);

        Organization experience_8 = new Organization("Alcatel",
                "http://www.alcatel.ru/",
                List.of(new Position(LocalDate.of(1997, 9, 1),
                        LocalDate.of(2005, 1, 1),
                        "Ведущий программист",
                        "Тестирование, отладка, внедрение ПО цифровой телефонной станции Alcatel 1000 S12" +
                                " (CHILL, ASM).")));
        organizations.add(experience_8);

        resume.getSections().put(SectionType.EXPERIENCE, new OrganizationSection(organizations));

        List<Organization> educations = new ArrayList<>();

        Organization education_1 = new Organization("Coursera",
                "https://www.coursera.org/course/progfun",
                List.of(new Position(LocalDate.of(2013, 3, 1),
                        LocalDate.of(2013, 5, 1),
                        "\t\"Functional Programming Principles in Scala\" by Martin Odersky",
                        null))
        );
        educations.add(education_1);

        Organization education_2 = new Organization("Luxoft",
                "http://www.luxoft-training.ru/training/catalog/course.html?ID=22366",
                List.of(new Position(LocalDate.of(2011, 3, 1),
                        LocalDate.of(2011, 4, 1),
                        "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                        null))
        );
        educations.add(education_2);

        Organization education_3 = new Organization("Siemens AG",
                "http://www.siemens.ru/",
                List.of(new Position(LocalDate.of(2005, 1, 1),
                        LocalDate.of(2005, 4, 1),
                        "3 месяца обучения мобильным IN сетям (Берлин)",
                        null))
        );
        educations.add(education_3);

        Organization education_4 = new Organization("Alcatel",
                "http://www.alcatel.ru/",
                List.of(new Position(LocalDate.of(1997, 9, 1),
                        LocalDate.of(1998, 3, 1),
                        "6 месяцев обучения цифровым телефонным сетям (Москва)",
                        null))
        );
        educations.add(education_4);

        Organization education_5 = new Organization("Санкт-Петербургский национальный исследовательский" +
                " университет информационных технологий, механики и оптики",
                "http://www.ifmo.ru/",
                List.of(new Position(LocalDate.of(1993, 9, 1),
                                LocalDate.of(1996, 7, 1),
                                "Аспирантура (программист С, С++)",
                                null),
                        new Position(LocalDate.of(1987, 9, 1),
                                LocalDate.of(1993, 7, 1),
                                "Инженер (программист Fortran, C)",
                                null))
        );
        educations.add(education_5);

        OrganizationSection educationInformation = new OrganizationSection(educations);

        Organization education_7 = new Organization("Заочная физико-техническая школа при МФТИ",
                "http://www.school.mipt.ru/",
                List.of(new Position(LocalDate.of(1984, 9, 1),
                        LocalDate.of(1987, 6, 1),
                        "Закончил с отличием",
                        null))
        );
        educations.add(education_7);

        resume.getSections().put(SectionType.EDUCATION, educationInformation);

        resume.getContacts().put(ContactType.TELEPHONE, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.EMAIL, "gkislin@yandex.ru");
        resume.getContacts().put(ContactType.LINKEDIN, "https://www.linkedin.com/in/gkislin");
        resume.getContacts().put(ContactType.GITHUB, "https://github.com/gkislin");
        resume.getContacts().put(ContactType.STACKOVERFLOW, "https://stackoverflow.com/users/548473");
        resume.getContacts().put(ContactType.HOMEPAGE, "http://gkislin.ru/");

        return resume;
    }

    public static void main(String[] args) {
        Resume resume = generateResume(UUID.randomUUID().toString(), "Ivan ivanov");

        for (Map.Entry<SectionType, AbstractSection> resumeSection : resume.getSections().entrySet()) {
            System.out.println(resumeSection.getKey().getTitle());
            System.out.println(resumeSection.getValue().toString());
            System.out.println();
        }

        for (Map.Entry<ContactType, String> contact : resume.getContacts().entrySet()) {
            System.out.println(contact.getKey().getTitle());
            System.out.println(contact.getValue());
            System.out.println();
        }
    }
}
