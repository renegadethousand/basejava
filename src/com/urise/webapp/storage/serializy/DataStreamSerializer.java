package com.urise.webapp.storage.serializy;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Link;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DataStreamSerializer implements StreamSerializer {

    private final Set<SectionType> textSectionSet = Set.of(
            SectionType.PERSONAL,
            SectionType.OBJECTIVE,
            SectionType.ACHIEVEMENT);

    private final Set<SectionType> listSectionSet = Set.of(
            SectionType.QUALIFICATIONS);

    private final Set<SectionType> experienceSectionSet = Set.of(
            SectionType.EXPERIENCE,
            SectionType.EDUCATION);

    @Override
    public void doWrite(Resume resume, OutputStream file) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(file)) {
            dataOutputStream.writeUTF(resume.getUuid());
            dataOutputStream.writeUTF(resume.getFullName());
            writeContacts(resume, dataOutputStream);
            writeSections(resume, dataOutputStream);
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            Resume resume = new Resume(dataInputStream.readUTF(), dataInputStream.readUTF());
            readContacts(dataInputStream, resume);
            readSections(dataInputStream, resume);
            return resume;
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dataOutputStream) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        dataOutputStream.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey().name());
            dataOutputStream.writeUTF(entry.getValue());
        }
    }

    private void writeSections(Resume resume, DataOutputStream dataOutputStream) throws IOException {
        Map<SectionType, Section> sections = resume.getSections();
        dataOutputStream.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry : sections.entrySet()) {
            SectionType sectionType = entry.getKey();
            dataOutputStream.writeUTF(sectionType.name());
            if (textSectionSet.contains(sectionType)) {
                writeTextSection(dataOutputStream, entry);
            } else if (listSectionSet.contains(sectionType)) {
                writeQualificationsSection(dataOutputStream, entry);
            } else if (experienceSectionSet.contains(sectionType)) {
                writeExperienceSection(dataOutputStream, entry);
            }
        }
    }

    private void writeTextSection(DataOutputStream dataOutputStream, Map.Entry<SectionType, Section> entry) throws IOException {
        dataOutputStream.writeUTF(((TextSection) entry.getValue()).getContent());
    }

    private void writeQualificationsSection(DataOutputStream dataOutputStream, Map.Entry<SectionType, Section> entry) throws IOException {
        List<String> items = ((ListSection) entry.getValue()).getItems();
        dataOutputStream.writeInt(items.size());
        for (int i = 0; i < items.size(); i++) {
            dataOutputStream.writeUTF(items.get(i));
        }
    }

    private void writeExperienceSection(DataOutputStream dataOutputStream, Map.Entry<SectionType, Section> entry) throws IOException {
        List<Organization> experienceList = ((OrganizationSection) entry.getValue()).getExperienceList();
        dataOutputStream.writeInt(experienceList.size());
        for (int i = 0; i < experienceList.size(); i++) {
            writeExperience(dataOutputStream, experienceList.get(i));
        }
    }

    private void writeExperience(DataOutputStream dataOutputStream, Organization organization) throws IOException {
        writeLink(dataOutputStream, organization.getHomePage());
        List<Organization.Position> positions = organization.getPositions();
        dataOutputStream.writeInt(positions.size());
        for (int j = 0; j < positions.size(); j++) {
            writePosition(dataOutputStream, positions.get(j));
        }
    }

    private void writeLink(DataOutputStream dataOutputStream, Link link) throws IOException {
        dataOutputStream.writeUTF(link.getName());
        if (link.getUrl() != null) {
            dataOutputStream.writeUTF(link.getUrl());
        } else {
            dataOutputStream.writeUTF("");
        }
    }

    private void writePosition(DataOutputStream dataOutputStream, Organization.Position position) throws IOException {
        dataOutputStream.writeUTF(position.getStartDate().toString());
        dataOutputStream.writeUTF(position.getEndDate().toString());
        dataOutputStream.writeUTF(position.getTitle());
        if (position.getDescription() != null) {
            dataOutputStream.writeUTF(position.getDescription());
        } else {
            dataOutputStream.writeUTF("");
        }
    }

    private void readContacts(DataInputStream dataInputStream, Resume resume) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
        }
    }

    private void readSections(DataInputStream dataInputStream, Resume resume) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            SectionType sectionType = SectionType.valueOf(dataInputStream.readUTF());
            if (textSectionSet.contains(sectionType)) {
                readTextSection(dataInputStream, resume, sectionType);
            } else if (listSectionSet.contains(sectionType)) {
                readQualificationsSection(dataInputStream, resume, sectionType);
            } else if (experienceSectionSet.contains(sectionType)) {
                readExperienceSection(dataInputStream, resume, sectionType);
            }
        }
    }

    private void readTextSection(DataInputStream dataInputStream, Resume resume, SectionType sectionType) throws IOException {
        resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
    }

    private void readQualificationsSection(DataInputStream dataInputStream, Resume resume, SectionType sectionType) throws IOException {
        int qualificationSize = dataInputStream.readInt();
        List<String> list = new ArrayList<>();
        for (int j = 0; j < qualificationSize; j++) {
            list.add(dataInputStream.readUTF());
        }
        resume.addSection(sectionType, new ListSection(list));
    }

    private void readExperienceSection(DataInputStream dataInputStream, Resume resume, SectionType sectionType) throws IOException {
        int educationSize = dataInputStream.readInt();
        List<Organization> organizations = new ArrayList<>();
        for (int j = 0; j < educationSize; j++) {
            organizations.add(new Organization(readLink(dataInputStream), readPositions(dataInputStream)));
        }
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    private Link readLink(DataInputStream dataInputStream) throws IOException {
        return new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
    }

    private List<Organization.Position> readPositions(DataInputStream dataInputStream) throws IOException {
        List<Organization.Position> positions = new ArrayList<>();
        int positionsSize = dataInputStream.readInt();
        for (int k = 0; k < positionsSize; k++) {
            Organization.Position position = new Organization.Position(
                    LocalDate.parse(dataInputStream.readUTF()),
                    LocalDate.parse(dataInputStream.readUTF()),
                    dataInputStream.readUTF(),
                    dataInputStream.readUTF()
            );
            positions.add(position);
        }
        return positions;
    }
}
