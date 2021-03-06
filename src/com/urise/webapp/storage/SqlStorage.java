package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.sql.SqlHelper;
import com.urise.webapp.util.JsonParser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
            deleteListSection(resume, connection);
            saveContacts(resume, connection);
            saveSection(resume, connection);
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
            saveSection(resume, connection);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.transactionalExecute(connection -> {
            Resume resume;
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("SELECT * FROM resume WHERE uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    throw new NotExistStorageException(uuid);
                }
                resume = new Resume(uuid, resultSet.getString("full_name"));
            }
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("SELECT * FROM contact WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    readContact(resultSet, resume);
                }
            }
            try (PreparedStatement preparedStatement
                         = connection.prepareStatement("SELECT * FROM section WHERE resume_uuid = ?")) {
                preparedStatement.setString(1, uuid);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    readSection(resultSet, resume);
                }
            }
            return resume;
        });
//        return sqlHelper.execute(
//                "Select r.uuid, " +
//                        "r.full_name, " +
//                        "c.type, " +
//                        "c.value, " +
//                        "c.resume_uuid," +
//                        "ls.type as ls_type," +
//                        "ls.content as ls_value," +
//                        "ls.resume_uuid as ls_resume_uuid " +
//                        "from resume r " +
//                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
//                        "LEFT JOIN section ls ON r.uuid = ls.resume_uuid " +
//                        "WHERE uuid = ?",
//                preparedStatement -> {
//                    preparedStatement.setString(1, uuid);
//                    final ResultSet resultSet = preparedStatement.executeQuery();
//                    if (!resultSet.next()) {
//                        throw new NotExistStorageException(uuid);
//                    }
//                    Resume resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
//                    do {
//                        readContact(resultSet, resume);
//                        readSection(resultSet, resume);
//                    } while (resultSet.next());
//                    return resume;
//                });
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
        Map<String, Resume> resumes = sqlHelper.execute(
                "Select * from resume ORDER BY full_name, uuid",
                (preparedStatement) -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Resume> resumeMap = new LinkedHashMap<>();
                    while (resultSet.next()) {
                        resumeMap.put(resultSet.getString("uuid"),
                                new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                    }
                    return resumeMap;
                });
        sqlHelper.execute(
                "Select * from contact",
                preparedStatement -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    while (resultSet.next()) {
                        Resume resume = resumes.get(resultSet.getString("resume_uuid"));
                        if (resume != null) {
                            readContact(resultSet, resume);
                        }
                    }
                    return null;
                });
        sqlHelper.execute(
                "Select * from section",
                preparedStatement -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Map<SectionType, Section>> resumeMap = new HashMap<>();
                    while (resultSet.next()) {
                        Resume resume = resumes.get(resultSet.getString("resume_uuid"));
                        if (resume != null) {
                            readSection(resultSet, resume);
                        }
                    }
                    return resumeMap;
                });

        return new ArrayList<>(resumes.values());
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

    private void deleteContacts(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(connection, resume, "DELETE FROM contact WHERE resume_uuid = ?");
    }

    private void deleteListSection(Resume resume, Connection connection) throws SQLException {
        deleteAttributes(connection, resume, "DELETE FROM section WHERE resume_uuid = ?");
    }

    private void deleteAttributes(Connection connection, Resume resume, String sql) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {
            preparedStatement.setString(1, resume.getUuid());
            preparedStatement.execute();
        }
    }

    private void saveSection(Resume resume, Connection connection) throws SQLException {
        try (PreparedStatement preparedStatement =
                     connection.prepareStatement("INSERT INTO section (resume_uuid, type, content) VALUES (?, ?, ?)")) {
            for (Map.Entry<SectionType, Section> sectionEntry : resume.getSections().entrySet()) {
                preparedStatement.setString(1, resume.getUuid());
                preparedStatement.setString(2, sectionEntry.getKey().name());
                preparedStatement.setString(3, JsonParser.write(sectionEntry.getValue(), Section.class));
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
        }
    }

    private void readContact(ResultSet resultSet, Resume resume) throws SQLException {
        if (resultSet.getString("value") != null) {
            String value = resultSet.getString("value");
            ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
            resume.setContact(contactType, value);
        }
    }

    private void readSection(ResultSet resultSet, Resume resume) throws SQLException {
        String content = resultSet.getString("content");
        if (content != null) {
            SectionType type = SectionType.valueOf(resultSet.getString("type"));
            resume.setSection(type, JsonParser.read(content, Section.class));
        }
    }
}
