package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

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
        sqlHelper.execute("UPDATE resume SET full_name = ? WHERE uuid = ?", preparedStatement -> {
            preparedStatement.setString(1, resume.getFullName());
            preparedStatement.setString(2, resume.getUuid());
            if (preparedStatement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                preparedStatement -> {
                    preparedStatement.setString(1, resume.getUuid());
                    preparedStatement.setString(2, resume.getFullName());
                    try {
                        preparedStatement.execute();
                    } catch (SQLException exception) {
                        if (exception.getSQLState().equals("23505")) {
                            throw new ExistStorageException(resume.getUuid());
                        } else {
                            throw new SQLException(exception);
                        }
                    }

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
                    return new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
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
        return sqlHelper.execute(
                "Select * from resume",
                (preparedStatement) -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    List<Resume> resumeList = new ArrayList<>();
                    while (resultSet.next()) {
                        resumeList.add(
                                new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"))
                        );
                    }
                    return resumeList;
                });
    }

    @Override
    public int size() {
        return sqlHelper.execute(
                "Select count(*) as count from resume",
                (preparedStatement) -> {
                    final ResultSet resultSet = preparedStatement.executeQuery();
                    resultSet.next();
                    return Integer.parseInt(resultSet.getString("count"));
                });
    }
}
