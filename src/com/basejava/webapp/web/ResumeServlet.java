package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ResumeServlet extends HttpServlet {

    private Storage storage;

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        storage = Config.get().getStorage();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        String action = request.getParameter("action");

        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;

        switch (action) {
            case "delete" -> {
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            }
            case "view" -> resume = storage.get(uuid);
            case "add" -> {
                resume = new Resume();
                resume.addContact(ContactType.MAIL, "");
                resume.addContact(ContactType.SKYPE, "");
                resume.addSection(SectionType.OBJECTIVE, new TextSection(""));
                resume.addSection(SectionType.PERSONAL, new TextSection(""));
                resume.addSection(SectionType.ACHIEVEMENT, new ListSection(""));
                resume.addSection(SectionType.QUALIFICATIONS, new ListSection(""));
                resume.addSection(SectionType.EDUCATION, new OrganizationSection(new Experience("", "",
                        new Experience.Position())));
                resume.addSection(SectionType.EXPERIENCE, new OrganizationSection(new Experience("", "",
                        new Experience.Position())));
            }
            case "edit" -> {
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE, PERSONAL -> {
                            if (section == null) {
                                section = new TextSection("");
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            if (section == null) {
                                section = new ListSection("");
                            }
                        }
                        case EDUCATION, EXPERIENCE -> {
                            if (section == null) {
                                section = new OrganizationSection(new Experience("", "",
                                        new Experience.Position()));
                            }
                        }
                    }
                    resume.addSection(type, section);
                }
            }
            default -> throw new IllegalArgumentException("Action " + action + " is illegal");
        }

        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        Resume resume;

        if (uuid == null || uuid.length() == 0) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            if (value != null && value.trim().length() != 0) {
                switch (sectionType.name()) {
                    case "OBJECTIVE", "PERSONAL" -> resume.addSection(sectionType, new TextSection(value));
                    case "ACHIEVEMENT", "QUALIFICATIONS" -> {
                        String[] array = value.split("\n");
                        ArrayList<String> result = new ArrayList<>();

                        for (String str : array) {
                            if (str != null && str.trim().length() != 0) {
                                result.add(str);
                            }
                        }
                        resume.addSection(sectionType, new ListSection(result));
                    }
                }
            }
        }

        if (uuid == null || uuid.length() == 0) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }
}
