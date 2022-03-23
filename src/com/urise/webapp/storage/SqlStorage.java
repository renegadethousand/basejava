package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.OrganizationSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import com.urise.webapp.sql.SqlExecute;
import com.urise.webapp.sql.SqlHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        this.sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume");
    }

    @Override
    public void update(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("UPDATE resume SET full_name = ? WHERE uuid = ?")) {
                preparedStatement.setString(1, resume.getFullName());
                preparedStatement.setString(2, resume.getUuid());
                if (preparedStatement.executeUpdate() == 0) {
                    throw new NotExistStorageException(resume.getUuid());
                }
            }
            deleteContacts(resume, connection);
            saveContacts(resume, connection);
            deleteListSection(resume, connection);
            saveListSection(resume, connection);
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.transactionalExecute(connection -> {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?, ?)")) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, resume.getFullName());
                preparedStatement.execute();
            }
            saveContacts(resume, connection);
            saveListSection(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "Select r.uuid, " +
                        "r.full_name, " +
                        "c.type, " +
                        "c.value, " +
                        "c.resume_uuid," +
                        "ls.type as ls_type," +
                        "ls.value as ls_value," +
                        "ls.resume_uuid as ls_resume_uuid " +
                        "from resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "LEFT JOIN list_section ls ON r.uuid = ls.resume_uuid " +
                        "WHERE uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
                    do {
                        readContact(resultSet, resume);
                        readSection(resultSet, resume);
                    } while (resultSet.next());
                    return resume;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    if (preparedStatement.executeUpdate() == 0) {
                        throw new NotExistStorageException(uuid);
                    }
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = sqlHelper.execute(
                "Select * from resume r ORDER BY full_name, uuid",
                (preparedStatement) -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Resume> resumeMap = new LinkedHashMap<>();
                    while (resultSet.next()) {
                        Resume resume = resumeMap.getOrDefault(resultSet.getString("uuid"),
                                new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                        resumeMap.putIfAbsent(resume.getUuid(), resume);
                    }
                    return new ArrayList<>(resumeMap.values());
                });
        Map<String, Map<ContactType, String>> contactsMap = sqlHelper.execute(
                "Select * from contact c",
                preparedStatement -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Map<ContactType, String>> resumeMap = new HashMap<>();
                    while (resultSet.next()) {
                        Map<ContactType, String> contacts = resumeMap.getOrDefault(
                                resultSet.getString("resume_uuid"), new HashMap<>());
                        resumeMap.putIfAbsent(resultSet.getString("resume_uuid"), contacts);
                        contacts.put(ContactType.valueOf(resultSet.getString("type")), resultSet.getString("value"));
                    }
                    return resumeMap;
                });
        Map<String, Map<SectionType, Section>> sectionsMap = sqlHelper.execute(
                "Select * from list_section ls",
                preparedStatement -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Map<SectionType, Section>> resumeMap = new HashMap<>();
                    while (resultSet.next()) {
                        if (resultSet.getString("type") != null) {
                            Map<SectionType, Section> sections = resumeMap.getOrDefault(
                                    resultSet.getString("resume_uuid"), new HashMap<>());
                            resumeMap.putIfAbsent(resultSet.getString("resume_uuid"), sections);
                            SectionType sectionType = SectionType.valueOf(resultSet.getString("type"));
                            switch (sectionType) {
                                case PERSONAL:
                                case OBJECTIVE:
                                    sections.put(sectionType, new TextSection(resultSet.getString("value")));
                                    break;
                                case ACHIEVEMENT:
                                case QUALIFICATIONS:
                                    List<String> list = Arrays.asList(resultSet.getString("value").split("\\n"));
                                    sections.put(sectionType, new ListSection(list));
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                    return resumeMap;
                });

        for (Resume resume : resumes) {
            Map<ContactType, String> contacts = contactsMap.get(resume.getUuid());
            if (contacts != null) {
                for (Map.Entry<ContactType, String> contact : contacts.entrySet()) {
                    resume.addContact(contact.getKey(), contact.getValue());
                }
            }
            Map<SectionType, Section> sections = sectionsMap.get(resume.getUuid());
            if (sections != null) {
                for (Map.Entry<SectionType, Section> section : sections.entrySet()) {
                    resume.addSection(section.getKey(), section.getValue());
                }
            }
        }
        return resumes;
    }

    @Override
    public int size() {
        return sqlHelper.execute(
                "Select count(*) as count from resume",
                (preparedStatement) -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    return resultSet.getInt("count");
                });
    }

    private void deleteContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void saveContacts(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, entry.getKey().name());
                preparedStatement.setString(3, entry.getValue());
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void deleteListSection(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("DELETE FROM list_section WHERE resume_uuid = ?")) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void saveListSection(Resume resume, Connection connection) throws SQLException {
        List<SectionType> listSectionType = List.of(
                SectionType.ACHIEVEMENT,
                SectionType.QUALIFICATIONS,
                SectionType.PERSONAL,
                SectionType.OBJECTIVE);
        for (SectionType sectionType : listSectionType) {
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("INSERT INTO list_section (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                Section section = resume.getSection(sectionType);
                switch (sectionType) {
                        case PERSONAL:
                        case OBJECTIVE:
                            preparedStatement.setString(1, resume.getUuid());
                            preparedStatement.setString(2, sectionType.name());
                            preparedStatement.setString(3, ((TextSection) section).getContent());
                            preparedStatement.addBatch();
                            break;
                        case ACHIEVEMENT:
                        case QUALIFICATIONS:
                            StringBuilder sectionText = new StringBuilder();
                            for (String item : ((ListSection) section).getItems()) {
                                if (sectionText.length() > 0) {
                                    sectionText.append("\n");
                                }
                                sectionText.append(item);
                            }
                            preparedStatement.setString(1, resume.getUuid());
                            preparedStatement.setString(2, sectionType.name());
                            preparedStatement.setString(3, sectionText.toString());
                            preparedStatement.addBatch();
                            break;
                        default:
                            break;
                    }
                preparedStatement.executeBatch();
            }
        }
    }

    private void readContact(ResultSet resultSet, Resume resume) throws SQLException {
        if (resultSet.getString("value") != null) {
            String value = resultSet.getString("value");
            ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
            resume.addContact(contactType, value);
        }
    }

    private void readSection(ResultSet resultSet, Resume resume) throws SQLException {
        if (resultSet.getString("ls_type") != null) {
            SectionType sectionType = SectionType.valueOf(resultSet.getString("ls_type"));
            switch (sectionType) {
                case PERSONAL:
                case OBJECTIVE:
                    resume.addSection(sectionType, new TextSection(resultSet.getString("ls_value")));
                    break;
                case ACHIEVEMENT:
                case QUALIFICATIONS:
                    List<String> list = Arrays.asList(resultSet.getString("ls_value").split("\\n"));
                    resume.addSection(sectionType, new ListSection(list));
                    break;
                default:
                    break;
            }
        }
    }
}
