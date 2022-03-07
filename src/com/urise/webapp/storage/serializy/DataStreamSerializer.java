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

public class DataStreamSerializer implements StreamSerializer {

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
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                case ACHIEVEMENT:
                    dataOutputStream.writeUTF(((TextSection) entry.getValue()).getContent());
                    break;
                case QUALIFICATIONS:
                    writeQualificationsSection(dataOutputStream, entry);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    writeExperienceSection(dataOutputStream, entry);
                    break;
                default:
                    break;
            }
        }
    }

    private void writeQualificationsSection(DataOutputStream dataOutputStream, Map.Entry<SectionType, Section> entry) throws IOException {
        List<String> items = ((ListSection) entry.getValue()).getItems();
        dataOutputStream.writeInt(items.size());
        for (String item : items) {
            dataOutputStream.writeUTF(item);
        }
    }

    private void writeExperienceSection(DataOutputStream dataOutputStream, Map.Entry<SectionType, Section> entry) throws IOException {
        List<Organization> experienceList = ((OrganizationSection) entry.getValue()).getExperienceList();
        dataOutputStream.writeInt(experienceList.size());
        for (Organization organization : experienceList) {
            writeExperience(dataOutputStream, organization);
        }
    }

    private void writeExperience(DataOutputStream dataOutputStream, Organization organization) throws IOException {
        writeLink(dataOutputStream, organization.getHomePage());
        List<Organization.Position> positions = organization.getPositions();
        dataOutputStream.writeInt(positions.size());
        for (Organization.Position position : positions) {
            writePosition(dataOutputStream, position);
        }
    }

    private void writeLink(DataOutputStream dataOutputStream, Link link) throws IOException {
        dataOutputStream.writeUTF(link.getName());
        dataOutputStream.writeUTF(link.getUrl() != null ? link.getUrl() : "");
    }

    private void writePosition(DataOutputStream dataOutputStream, Organization.Position position) throws IOException {
        writeLocalDate(position.getStartDate(), dataOutputStream);
        writeLocalDate(position.getEndDate(), dataOutputStream);
        dataOutputStream.writeUTF(position.getTitle());
        dataOutputStream.writeUTF(position.getDescription() != null ? position.getDescription() : "");
    }

    private void writeLocalDate(LocalDate date, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(String.valueOf(date.getYear()));
        dataOutputStream.writeUTF(String.valueOf(date.getMonth().getValue()));
        dataOutputStream.writeUTF(String.valueOf(date.getDayOfMonth()));
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
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                case ACHIEVEMENT:
                    resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
                    break;
                case QUALIFICATIONS:
                    readQualificationsSection(dataInputStream, resume, sectionType);
                    break;
                case EXPERIENCE:
                case EDUCATION:
                    readExperienceSection(dataInputStream, resume, sectionType);
                    break;
                default:
                    break;
            }
        }
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
                    readLocalDate(dataInputStream),
                    readLocalDate(dataInputStream),
                    dataInputStream.readUTF(),
                    dataInputStream.readUTF()
            );
            positions.add(position);
        }
        return positions;
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(
                Integer.parseInt(dataInputStream.readUTF()),
                Integer.parseInt(dataInputStream.readUTF()),
                Integer.parseInt(dataInputStream.readUTF()));

    }
}
