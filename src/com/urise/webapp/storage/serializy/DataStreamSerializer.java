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
            String uuid = dataInputStream.readUTF();
            String fullName = dataInputStream.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readContacts(dataInputStream, resume);
            readSections(dataInputStream, resume);
            return resume;
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
            if (sectionType == SectionType.PERSONAL
                    || sectionType == SectionType.OBJECTIVE
                    || sectionType == SectionType.ACHIEVEMENT) {
                resume.addSection(sectionType, new TextSection(dataInputStream.readUTF()));
            } else if (sectionType == SectionType.QUALIFICATIONS) {
                int qualificationSize = dataInputStream.readInt();
                List<String> list = new ArrayList<>();
                for (int j = 0; j < qualificationSize; j++) {
                    list.add(dataInputStream.readUTF());
                }
                resume.addSection(sectionType, new ListSection(list));
            } else if (sectionType == SectionType.EXPERIENCE || sectionType == SectionType.EDUCATION) {
                int educationSize = dataInputStream.readInt();
                List<Organization> organizations = new ArrayList<>();
                for (int j = 0; j < educationSize; j++) {
                    Link link = new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
                    int positionsSize = dataInputStream.readInt();
                    List<Organization.Position> positions = new ArrayList<>();
                    for (int k = 0; k < positionsSize; k++) {
                        Organization.Position position = new Organization.Position(
                                LocalDate.parse(dataInputStream.readUTF()),
                                LocalDate.parse(dataInputStream.readUTF()),
                                dataInputStream.readUTF(),
                                dataInputStream.readUTF()
                        );
                        positions.add(position);
                    }
                    organizations.add(new Organization(link, positions));
                }
                resume.addSection(sectionType, new OrganizationSection(organizations));
            }
        }
    }

    private void writeContacts(Resume resume, DataOutputStream dataOutputStream) throws IOException {
        Map<ContactType, String> contacts = resume.getContacts();
        dataOutputStream.writeInt(contacts.size());
        for (Map.Entry<ContactType, String> entry: contacts.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey().name());
            dataOutputStream.writeUTF(entry.getValue());
        }
    }

    private void writeSections(Resume resume, DataOutputStream dataOutputStream) throws IOException {
        Map<SectionType, Section> sections = resume.getSections();
        dataOutputStream.writeInt(sections.size());
        for (Map.Entry<SectionType, Section> entry: sections.entrySet()) {
            dataOutputStream.writeUTF(entry.getKey().name());
            if (entry.getKey() == SectionType.PERSONAL
                    || entry.getKey() == SectionType.OBJECTIVE
                    || entry.getKey() == SectionType.ACHIEVEMENT) {
                dataOutputStream.writeUTF(((TextSection) entry.getValue()).getContent());
            } else if (entry.getKey() == SectionType.QUALIFICATIONS) {
                List<String> items = ((ListSection) entry.getValue()).getItems();
                dataOutputStream.writeInt(items.size());
                for (int i = 0; i < items.size(); i++) {
                    dataOutputStream.writeUTF(items.get(i));
                }
            } else if (entry.getKey() == SectionType.EXPERIENCE || entry.getKey() == SectionType.EDUCATION) {
                List<Organization> experienceList = ((OrganizationSection) entry.getValue()).getExperienceList();
                dataOutputStream.writeInt(experienceList.size());
                for (int i = 0; i < experienceList.size(); i++) {
                    Link homePage = experienceList.get(i).getHomePage();
                    dataOutputStream.writeUTF(homePage.getName());
                    if (homePage.getUrl() != null) {
                        dataOutputStream.writeUTF(homePage.getUrl());
                    } else {
                        dataOutputStream.writeUTF("");
                    }
                    List<Organization.Position> positions = experienceList.get(i).getPositions();
                    dataOutputStream.writeInt(positions.size());
                    for (int j = 0; j < positions.size(); j++) {
                        Organization.Position position = positions.get(j);
                        dataOutputStream.writeUTF(position.getStartDate().toString());
                        dataOutputStream.writeUTF(position.getEndDate().toString());
                        dataOutputStream.writeUTF(position.getTitle());
                        if (position.getDescription() != null) {
                            dataOutputStream.writeUTF(position.getDescription());
                        } else {
                            dataOutputStream.writeUTF("");
                        }
                    }
                }
            }
        }
    }
}
