package com.urise.webapp.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class BulletedListSection extends AbstractSection implements Serializable {

    private final List<String> skills;

    public BulletedListSection(List<String> items) {
        Objects.requireNonNull(items, "items must not be null");
        this.skills = items;
    }

    public List<String> getSkills() {
        return skills;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BulletedListSection that = (BulletedListSection) o;
        return skills.equals(that.skills);
    }

    @Override
    public int hashCode() {
        return Objects.hash(skills);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : skills) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
