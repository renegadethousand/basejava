package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section {

    private final List<Organization> organizations;

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organizations must not be null");
        this.organizations = organizations;
    }

    public List<Organization> getExperienceList() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organization organization : organizations) {
            stringBuilder.append(organization.getHomePage()).append("\n");
            if (organization.getTitle() != null) {
                stringBuilder.append(organization.getTitle()).append("\n");
            }
            stringBuilder.append(organization.getStartDate()).append("\n");
            stringBuilder.append(organization.getEndDate()).append("\n");
            stringBuilder.append(organization.getDescription()).append("\n");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public class Experiense {

        String title;
        String position;
        LocalDate startDate;
        LocalDate endDate;
        String text;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}