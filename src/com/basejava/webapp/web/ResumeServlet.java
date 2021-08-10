package com.basejava.webapp.web;

import com.basejava.webapp.Config;
import com.basejava.webapp.model.*;
import com.basejava.webapp.storage.Storage;
import com.basejava.webapp.util.DateUtil;
import com.basejava.webapp.util.HtmlUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
            case "add" -> resume = Resume.EMPTY;
            case "edit" -> {
                resume = storage.get(uuid);
                for (SectionType type : SectionType.values()) {
                    AbstractSection section = resume.getSection(type);
                    switch (type) {
                        case OBJECTIVE, PERSONAL -> {
                            if (section == null) {
                                section = TextSection.EMPTY;
                            }
                        }
                        case ACHIEVEMENT, QUALIFICATIONS -> {
                            if (section == null) {
                                section = ListSection.EMPTY;
                            }
                        }
                        case EDUCATION, EXPERIENCE -> {
                            OrganizationSection orgSection = (OrganizationSection) section;
                            ArrayList<Experience> organizations = new ArrayList<>();
                            organizations.add(Experience.EMPTY);
                            if (orgSection != null) {
                                for (Experience org : orgSection.getExperienceList()) {
                                    List<Experience.Position> positions = new ArrayList<>(org.getPositionList());
                                    positions.add(Experience.Position.EMPTY);
                                    organizations.add(new Experience(org.getLink(), positions));
                                }
                            }
                            section = new OrganizationSection(organizations);
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

        if (HtmlUtil.isEmpty(uuid)) {
            resume = new Resume(fullName);
        } else {
            resume = storage.get(uuid);
            resume.setFullName(fullName);
        }

        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (HtmlUtil.isEmpty(value)) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }

        for (SectionType sectionType : SectionType.values()) {
            String value = request.getParameter(sectionType.name());
            String[] values = request.getParameterValues(sectionType.name());
            if (HtmlUtil.isEmpty(value) && values.length < 2) {
                resume.getSections().remove(sectionType);
            } else {
                switch (sectionType) {
                    case OBJECTIVE, PERSONAL -> resume.addSection(sectionType, new TextSection(value));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        String[] array = value.split("\n");
                        ArrayList<String> result = new ArrayList<>();

                        for (String str : array) {
                            if (!HtmlUtil.isEmpty(str)) {
                                result.add(str);
                            }
                        }
                        resume.addSection(sectionType, new ListSection(result));
                    }
                    case EDUCATION, EXPERIENCE -> {
                        List<Experience> orgs = new ArrayList<>();
                        String[] urls = request.getParameterValues(sectionType.name() + "url");
                        for (int i = 0; i < values.length; i++) {
                            String name = values[i];
                            if (!HtmlUtil.isEmpty(name)) {
                                List<Experience.Position> positions = new ArrayList<>();
                                String index = sectionType.name() + i;
                                String[] startDates = request.getParameterValues(index + "startDate");
                                String[] endDates = request.getParameterValues(index + "endDate");
                                String[] titles = request.getParameterValues(index + "title");
                                String[] descriptions = request.getParameterValues(index + "description");
                                for (int j = 0; j < titles.length; j++) {
                                    if (!HtmlUtil.isEmpty(titles[j])) {
                                        positions.add(new Experience.Position(DateUtil.parse(startDates[j]), DateUtil.parse(endDates[j]), titles[j], descriptions[j]));
                                    }
                                }
                                orgs.add(new Experience(new Link(name, urls[i]), positions));
                            }
                        }
                        resume.addSection(sectionType, new OrganizationSection(orgs));
                    }
                }
            }
        }

        if (HtmlUtil.isEmpty(uuid)) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }
}
