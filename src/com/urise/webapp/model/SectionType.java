package com.urise.webapp.model;

import org.w3c.dom.Text;

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
    EXPERIENCE("Опыт работы"),
    EDUCATION("Образование");

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
}
