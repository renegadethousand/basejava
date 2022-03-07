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
import java.util.Collection;
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
        writeWithExeption(dataOutputStream, contacts.entrySet(), entry -> {
            dataOutputStream.writeUTF(entry.getKey().name());
            dataOutputStream.writeUTF(entry.getValue());
        });
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
        writeWithExeption(dataOutputStream, experienceList, organization -> {
            writeExperience(dataOutputStream, organization);
        });
    }

    private void writeExperience(DataOutputStream dataOutputStream, Organization organization) throws IOException {
        writeLink(dataOutputStream, organization.getHomePage());
        writeWithExeption(dataOutputStream, organization.getPositions(), position -> {
            writeLocalDate(position.getStartDate(), dataOutputStream);
            writeLocalDate(position.getEndDate(), dataOutputStream);
            dataOutputStream.writeUTF(position.getTitle());
            dataOutputStream.writeUTF(position.getDescription() != null ? position.getDescription() : "");
        });
    }

    private void writeLink(DataOutputStream dataOutputStream, Link link) throws IOException {
        dataOutputStream.writeUTF(link.getName());
        dataOutputStream.writeUTF(link.getUrl() != null ? link.getUrl() : "");
    }

    private void writeLocalDate(LocalDate date, DataOutputStream dataOutputStream) throws IOException {
        dataOutputStream.writeUTF(String.valueOf(date.getYear()));
        dataOutputStream.writeUTF(String.valueOf(date.getMonth().getValue()));
        dataOutputStream.writeUTF(String.valueOf(date.getDayOfMonth()));
    }

    private <T> void writeWithExeption(DataOutputStream dataOutputStream, Collection<T> collection, ConsumerWithException<T> consumer) throws IOException {
        dataOutputStream.writeInt(collection.size());
        for (T item : collection) {
            consumer.accept(item);
        }
    }

    private void readContacts(DataInputStream dataInputStream, Resume resume) throws IOException {
        forEachReadVoidWithException(dataInputStream, () -> {
            resume.addContact(ContactType.valueOf(dataInputStream.readUTF()), dataInputStream.readUTF());
        });
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
        List<String> list = readWithException(dataInputStream, dataInputStream::readUTF);
        resume.addSection(sectionType, new ListSection(list));
    }

    private void readExperienceSection(DataInputStream dataInputStream, Resume resume, SectionType sectionType) throws IOException {
        List<Organization> organizations = readWithException(dataInputStream, () -> {
            return new Organization(readLink(dataInputStream), readPositions(dataInputStream));
        });
        resume.addSection(sectionType, new OrganizationSection(organizations));
    }

    private Link readLink(DataInputStream dataInputStream) throws IOException {
        return new Link(dataInputStream.readUTF(), dataInputStream.readUTF());
    }

    private List<Organization.Position> readPositions(DataInputStream dataInputStream) throws IOException {
        return readWithException(dataInputStream, () -> {
            return new Organization.Position(
                    readLocalDate(dataInputStream),
                    readLocalDate(dataInputStream),
                    dataInputStream.readUTF(),
                    dataInputStream.readUTF()
            );
        });
    }

    private LocalDate readLocalDate(DataInputStream dataInputStream) throws IOException {
        return LocalDate.of(
                Integer.parseInt(dataInputStream.readUTF()),
                Integer.parseInt(dataInputStream.readUTF()),
                Integer.parseInt(dataInputStream.readUTF()));

    }

    private <T> List<T> readWithException(DataInputStream dataInputStream, SupplierWithException<T> supplier) throws IOException {
        int size = dataInputStream.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(supplier.apply());
        }
        return list;
    }

    private <T> void forEachReadVoidWithException(DataInputStream dataInputStream, VoidSupplierWithException supplier) throws IOException {
        int size = dataInputStream.readInt();
        for (int i = 0; i < size; i++) {
            supplier.apply();
        }
    }
}
