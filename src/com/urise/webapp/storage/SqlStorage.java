package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public static final String[] EMPTY_PARAMS = {};
    public final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.sqlHelper = new SqlHelper(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", (preparedStatement) -> {
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        if (get(resume.getUuid()) == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
        sqlHelper.execute("UPDATE resume SET full_name = ?", (preparedStatement) -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.execute();
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        try {
            get(resume.getUuid());
            throw new ExistStorageException(resume.getUuid());
        } catch (NotExistStorageException ignored) {
        }
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                (preparedStatement) -> {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, resume.getFullName());
                    preparedStatement.execute();
                    return null;
                });
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.execute(
                "Select * from resume WHERE uuid = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name"));
                });
    }

    @Override
    public void delete(String uuid) {
        if (get(uuid) == null) {
            throw new NotExistStorageException(uuid);
        }
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?",
                (preparedStatement) -> {
                    preparedStatement.setString(1, uuid);
                    preparedStatement.execute();
                    return null;
                });
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlHelper.execute(
                "Select * from resume",
                (preparedStatement) -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    List<Resume> resumeList = new ArrayList<>();
                    while (resultSet.next()) {
                        resumeList.add(
                                new Resume(resultSet.getString("uuid").trim(), resultSet.getString("full_name"))
                        );
                    }
                    return resumeList;
                });
    }

    @Override
    public int size() {
        return getAllSorted().size();
    }
}
