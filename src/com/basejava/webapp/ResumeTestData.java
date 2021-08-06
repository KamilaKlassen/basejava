package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.MOBILE, "+7(921) 855-0482");
        resume.addContact(ContactType.SKYPE, "grigory.kislin");
        resume.addContact(ContactType.MAIL, "gkislin@yandex.ru");
        resume.addContact(ContactType.GITHUB, "github.com/gkislin");

        resume.addSection(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.",
                "Реализация протоколов по приему платежей всех основных платежных системы России (Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа."));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Java Frameworks: Java 8 (Time API, Streams), Guava, Java Executor, MyBatis, Spring (MVC, Security, Data, Clouds, Boot), JPA (Hibernate, EclipseLink), Guice, GWT(SmartGWT, ExtGWT/GXT), Vaadin, Jasperreports, " +
                        "Apache Commons, Eclipse SWT, JUnit, Selenium (htmlelements)."));

        resume.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Experience("Luxoft", "https://luxoft.ru", new Experience.Position(2010, Month.DECEMBER,
                                2012, Month.APRIL, "Luxoft (Deutsche Bank)", "Ведущий программист\n" +
                                "Участие в проекте Deutsche Bank CRM (WebLogic, Hibernate, Spring, Spring MVC, SmartGWT, GWT, " +
                                "Jasper, Oracle). Реализация клиентской и серверной части CRM. Реализация RIA-приложения для " +
                                "администрирования, мониторинга и анализа результатов в области алгоритмического трейдинга. JPA, " +
                                "Spring, Spring-MVC, GWT, ExtGWT (GXT), Highstock, Commet, HTML5.")),
                        new Experience("Yota", "https://www.yota.ru/", new Experience.Position(2008, Month.JUNE,
                                2010, Month.DECEMBER, "Yota", "Ведущий специалист\n" +
                                "Дизайн и имплементация Java EE фреймворка для отдела \"Платежные Системы\" " +
                                "(GlassFish v2.1, v3, OC4J, EJB3, JAX-WS RI 2.1, Servlet 2.4, JSP, JMX, JMS, Maven2). " +
                                "Реализация администрирования, статистики и мониторинга фреймворка. " +
                                "Разработка online JMX клиента (Python/ Jython, Django, ExtJS)")),
                        new Experience("Enkata", "http://enkata.com/", new Experience.Position(2007, Month.MARCH,
                                2008, Month.JUNE, "Enkata", "\tРазработчик ПО\n" +
                                "Реализация клиентской (Eclipse RCP) и серверной (JBoss 4.2, Hibernate 3.0, Tomcat, JMS)" +
                                " частей кластерного J2EE приложения (OLAP, Data mining)."))));

        resume.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Experience("Заочная физико-техническая школа при МФТИ",
                                "http://www.school.mipt.ru/", new Experience.Position(1984, Month.SEPTEMBER, 1987, Month.JUNE,
                                "Заочная физико-техническая школа при МФТИ", "Закончил с отличием")),
                        new Experience("Санкт-Петербургский национальный исследовательский университет ", "https://itmo.ru/ru/",
                                new Experience.Position(1993, Month.SEPTEMBER,
                                        1996, Month.JULY, "Санкт-Петербургский национальный исследовательский университет " +
                                        "информационных технологий, механики и оптики", "Аспирантура (программист С, С++)"),
                                new Experience.Position(1987, Month.SEPTEMBER, 1993, Month.JULY,
                                        "Санкт-Петербургский национальный исследовательский университет информационных технологий, " +
                                                "механики и оптики", "Инженер (программист Fortran, C)")),
                        new Experience("Luxoft", "https://luxoft.ru", new Experience.Position(2011, Month.MARCH,
                                2011, Month.APRIL, "Luxoft", "Курс \"Объектно-ориентированный анализ ИС. " +
                                "Концептуальное моделирование на UML.\""))));

        return resume;
    }

    public static void main(String[] args) {
        //create new Resume
        Resume resume = new Resume("Григорий Кислин");

        //add contact information
        resume.getContacts().put(ContactType.PHONE, "+7(921) 855-0482");
        resume.getContacts().put(ContactType.SKYPE, "grigory.kislin");
        resume.getContacts().put(ContactType.MAIL, "gkislin@yandex.ru");

        //add personal information
        resume.getSections().put(SectionType.OBJECTIVE, new TextSection("Ведущий стажировок и корпоративного обучения " +
                "по Java Web и Enterprise технологиям"));
        resume.getSections().put(SectionType.PERSONAL, new TextSection("Аналитический склад ума, сильная логика, креативность, " +
                "инициативность. Пурист кода и архитектуры."));

        //create achievement list
        List<String> achievementList = new ArrayList<>();

        //fill in the achievement list
        achievementList.add("Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. " +
                "Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk.");
        achievementList.add("Реализация протоколов по приему платежей всех основных платежных системы России " +
                "(Cyberplat, Eport, Chronopay, Сбербанк), Белоруcсии(Erip, Osmp) и Никарагуа.");

        //add achievement list to resume
        resume.getSections().put(SectionType.ACHIEVEMENT, new ListSection(achievementList));

        //output
        System.out.println(resume.getFullName());

        for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue());
        }
        for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
            System.out.println(entry.getKey().getTitle() + ": " + entry.getValue().toString());
        }
    }
}
