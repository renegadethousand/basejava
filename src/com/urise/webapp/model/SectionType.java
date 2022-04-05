package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.List;

public enum SectionType {
    PERSONAL("Личные качества") {
        @Override
        public String toHtml0(Section section) {
            return getContentTextSection(section);
        }
    },
    OBJECTIVE("Позиция") {
        @Override
        public String toHtml0(Section section) {
            return getContentTextSection(section);
        }
    },
    ACHIEVEMENT("Достижения") {
        @Override
        public String toHtml0(Section section) {
            return getContentListSection(section);
        }
    },
    QUALIFICATIONS("Квалификация") {
        @Override
        public String toHtml0(Section section) {
            return getContentListSection(section);
        }
    },
    EXPERIENCE("Опыт работы") {
        @Override
        public String toHtml0(Section section) {
            return getContentOrganizationSection(section);
        }
    },
    EDUCATION("Образование") {
        @Override
        public String toHtml0(Section section) {
            return getContentOrganizationSection(section);
        }
    };

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    protected String toHtml0(Section section) {
        return title + ": " + section.toString();
    }

    public String toHtml(Section section) {
        return (section == null) ? "" : toHtml0(section);
    }

    public String getContentTextSection(Section section) {
        String value = ((TextSection) section).getContent();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h3>")
                .append(getTitle())
                .append("</h3>")
                .append(value);
        return stringBuilder.toString();
    }

    public String getContentListSection(Section section) {
        List<String> value = ((ListSection) section).getItems();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h3>")
                .append(getTitle())
                .append("</h3>");
        value.forEach(el -> stringBuilder.append(el).append("<br>"));
        return stringBuilder.toString();
    }

    public String getContentOrganizationSection(Section section) {
        List<Organization> value = ((OrganizationSection) section).getOrganizationsList();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<h3>")
                .append(getTitle())
                .append("</h3>");

        for (Organization organization : value) {

            stringBuilder.append("<a ")
                    .append("href=\"")
                    .append(organization.getHomePage().getUrl())
                    .append("\">")
                    .append(organization.getHomePage().getName())
                    .append("</a></br></br>");
            for (Organization.Position position : organization.getPositions()) {
                stringBuilder.append(position.getStartDate())
                        .append(" - ")
                        .append(
                                position.getEndDate().isEqual(LocalDate.of(1,1,1))
                                        ? "Настоящее время" : position.getEndDate())
                        .append("</br>")
                        .append("</br>")
                        .append(position.getTitle())
                        .append("</br>")
                        .append("</br>");
                if (position.getDescription() != null) {
                    stringBuilder.append(position.getDescription())
                            .append("</br>")
                            .append("</br>");
                }
            }
        }
        return stringBuilder.toString();
    }
}
