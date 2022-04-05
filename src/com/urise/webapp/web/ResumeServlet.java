package com.urise.webapp.web;

import com.urise.webapp.Config;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Link;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import com.urise.webapp.storage.SqlStorage;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.urise.webapp.model.SectionType.*;

public class ResumeServlet extends HttpServlet {

    SqlStorage storage;

    @Override
    public void init() throws ServletException {
        super.init();
        storage = (SqlStorage) Config.get().getSqlStorage();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String uuid = request.getParameter("uuid");
        String fullName = request.getParameter("fullName");
        boolean isNewResume = uuid.equals("");
        Resume resume = null;
        if (!isNewResume) {
            resume = storage.get(uuid);
        }
        if (fullName != null && fullName.trim().length() != 0) {
            if (isNewResume) {
                resume = new Resume(fullName);
            } else {
                resume.setFullName(fullName);
            }
        } else {
            throw new RuntimeException("Незаполнено имя!");
        }
        for (ContactType type : ContactType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                resume.addContact(type, value);
            } else {
                resume.getContacts().remove(type);
            }
        }
        for (SectionType type : SectionType.values()) {
            String value = request.getParameter(type.name());
            if (value != null && value.trim().length() != 0) {
                switch (type) {
                    case PERSONAL:
                    case OBJECTIVE:
                        resume.addSection(type, new TextSection(value));
                        break;
                    case ACHIEVEMENT:
                    case QUALIFICATIONS:
                        resume.addSection(type, new ListSection(Arrays.stream(value.split("\n"))
                                .map(String::trim)
                                .filter(el -> !el.isEmpty())
                                .collect(Collectors.toList())));
                        break;
                    default:
                        break;
                }
            } else {
                resume.getSections().remove(type);
            }
        }
        readOrganizationSection(EXPERIENCE, resume, request);
        readOrganizationSection(EDUCATION, resume, request);
        if (isNewResume) {
            storage.save(resume);
        } else {
            storage.update(resume);
        }
        response.sendRedirect("resume");
    }

    private void readOrganizationSection(SectionType type, Resume resume, HttpServletRequest request) {
        List<Organization> organizationList = new ArrayList<>();
        int organizations_count = Integer.parseInt(request.getParameter(type.name() + "_organizations"));
        for (int i = 0; i < organizations_count; i++) {
            if (request.getParameter(type.name() + "_name_" + i).isEmpty()) {
                continue;
            }
            int positions_count = Integer.parseInt(request.getParameter(type.name() + "_" + i + "_positions"));
            List<Organization.Position> positionList = new ArrayList<>();
            for (int j = 0; j < positions_count; j++) {
                if (request.getParameter(type.name() + "_title_" + i + "_" + j).isEmpty()) {
                    continue;
                }
                positionList.add(new Organization.Position(
                        LocalDate.parse(request.getParameter(type.name() + "_startDate_" + i + "_" + j)),
                        LocalDate.parse(request.getParameter(type.name() + "_endDate_" + i + "_" + j)),
                        request.getParameter(type.name() + "_title_" + i + "_" + j),
                        request.getParameter(type.name() + "_description_" + i + "_" + j)));
            }
            organizationList.add(new Organization(
                    new Link(request.getParameter(type.name() + "_name_" + i),
                            request.getParameter(type.name() + "_page_" + i)),
                    positionList));
        }
        resume.addSection(type, new OrganizationSection(organizationList));
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        final String uuid = request.getParameter("uuid");
        final String action = request.getParameter("action");
        if (action == null) {
            request.setAttribute("resumes", storage.getAllSorted());
            request.getRequestDispatcher("/WEB-INF/jsp/list.jsp").forward(request, response);
            return;
        }
        Resume resume;
        switch (action) {
            case "delete":
                storage.delete(uuid);
                response.sendRedirect("resume");
                return;
            case "view":
            case "edit":
                resume = storage.get(uuid);
                break;
            case "add":
                resume = new Resume();
                break;
            default:
                throw new IllegalArgumentException("Action " + action + " is illegal");
        }
        resume.getSections().putIfAbsent(EXPERIENCE, new OrganizationSection(new ArrayList<>()));
        resume.getSections().putIfAbsent(EDUCATION, new OrganizationSection(new ArrayList<>()));
        request.setAttribute("resume", resume);
        request.getRequestDispatcher(
                ("view".equals(action) ? "/WEB-INF/jsp/view.jsp" : "/WEB-INF/jsp/edit.jsp")
        ).forward(request, response);
    }
}
