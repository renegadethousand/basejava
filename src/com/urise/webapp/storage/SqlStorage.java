package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlExecute;
import com.urise.webapp.sql.SqlHelper;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {

    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
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
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("UPDATE contact SET  value = ? WHERE resume_uuid = ? AND type = ?")) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    preparedStatement.setString(1, entry.getKey().name());
                    preparedStatement.setString(2, entry.getValue());
                    preparedStatement.setString(3, resume.getUuid());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
            }
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
            try (PreparedStatement preparedStatement =
                         connection.prepareStatement("INSERT INTO contact (resume_uuid, type, value) VALUES (?, ?, ?)")) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, entry.getKey().name());
                    preparedStatement.setString(3, entry.getValue());
                    preparedStatement.addBatch();
                }
                preparedStatement.executeBatch();
                return null;
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "Select * from resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid " +
                        "WHERE uuid = ?",
                preparedStatement -> {
                    preparedStatement.setString(1, uuid);
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume resume = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
                    do {
                        if (resultSet.getString("value") != null) {
                            String value = resultSet.getString("value");
                            ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
                            resume.addContact(contactType, value);
                        }
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
        return  sqlHelper.execute(
                "Select * from resume r " +
                        "LEFT JOIN contact c ON r.uuid = c.resume_uuid",
                (preparedStatement) -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    Map<String, Resume> resumeMap = new HashMap<>();
                    while (resultSet.next()) {
                        Resume resume = resumeMap.getOrDefault(resultSet.getString("uuid"),
                                new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
                        resumeMap.putIfAbsent(resume.getUuid(), resume);
                        if (resultSet.getString("value") != null) {
                            String value = resultSet.getString("value");
                            ContactType contactType = ContactType.valueOf(resultSet.getString("type"));
                            resume.addContact(contactType, value);
                        }
                    }
                    return new ArrayList<>(resumeMap.values());
                });
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
}
