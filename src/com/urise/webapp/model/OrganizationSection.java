package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends Section implements Serializable {

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
        return Objects.equals(organizations, that.organizations);
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
            for (Position position : organization.getPositions()) {
                if (position.getTitle() != null) {
                    stringBuilder.append(position.getTitle()).append("\n");
                }
                stringBuilder.append(position.getStartDate()).append("\n");
                stringBuilder.append(position.getEndDate()).append("\n");
                stringBuilder.append(position.getDescription()).append("\n");
                stringBuilder.append("\n");
            };
        }
        return stringBuilder.toString();
    }

}
