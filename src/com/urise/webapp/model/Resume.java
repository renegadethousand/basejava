package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private final String fullName;
    private final Map<SectionType, ResumeSection> resumeInfo = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "uuid must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFullName() {
        return fullName;
    }

    public Map<SectionType, ResumeSection> getResumeInfo() {
        return resumeInfo;
    }

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    @Override
    public String toString() {
        return "Resume{" +
                "uuid='" + uuid + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName)
                && Objects.equals(resumeInfo, resume.resumeInfo) && Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, resumeInfo, contacts);
    }

    @Override
    public int compareTo(Resume resume) {
        int compareToFullName = fullName.compareTo(resume.getFullName());
        return compareToFullName == 0 ? uuid.compareTo(resume.getUuid()) : compareToFullName;
    }
}
