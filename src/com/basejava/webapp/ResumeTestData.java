package com.basejava.webapp;

import com.basejava.webapp.model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ResumeTestData {

    public static Resume fillResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid, fullName);

        resume.addContact(ContactType.MOBILE, "Mobile");
        resume.addContact(ContactType.SKYPE, "Skype");

        resume.addSection(SectionType.PERSONAL, new TextSection("Personal"));
        resume.addSection(SectionType.OBJECTIVE, new TextSection("Objective"));

        resume.addSection(SectionType.ACHIEVEMENT, new ListSection("Achievement 1", "Achievement 2"));
        resume.addSection(SectionType.QUALIFICATIONS, new ListSection("Qualification 1", "Qualification 2"));

//        resume.addSection(SectionType.EXPERIENCE,
//                new OrganizationSection(
//                        new Experience("Organization", null,
//                                new Experience.Position(2010, Month.JANUARY, "Position", "Description"),
//                                new Experience.Position(2005, Month.JANUARY, 2009, Month.JANUARY, "Position", "Description"))));
//        resume.addSection(SectionType.EDUCATION,
//                new OrganizationSection(
//                        new Experience("Organisation", "URL",
//                                new Experience.Position(1996, Month.JANUARY, 2000, Month.JANUARY, "Title", null),
//                                new Experience.Position(2001, Month.MAY, 2005, Month.JANUARY, "Title", "Description")),
//                        new Experience("Organization", "URL")));
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
