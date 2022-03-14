package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlStorage implements Storage {

    public static final String[] EMPTY_PARAMS = {};
    public final SqlHelper sqlHelper;

    public SqlStorage(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    @Override
    public void clear() {
        sqlHelper.execute("DELETE FROM resume", EMPTY_PARAMS);
    }

    @Override
    public void update(Resume resume) {
        if (get(resume.getUuid()) == null) {
            throw new NotExistStorageException(resume.getUuid());
        }
        sqlHelper.execute("UPDATE resume SET full_name = ?", new String[]{resume.getFullName()});
    }

    @Override
    public void save(Resume resume) {
        try {
            get(resume.getUuid());
            throw new ExistStorageException(resume.getUuid());
        } catch (NotExistStorageException ignored) {
        }
        sqlHelper.execute("INSERT INTO resume (uuid, full_name) VALUES (?, ?)",
                new String[]{
                        resume.getUuid(),
                        resume.getFullName()
                });
    }

    @Override
    public Resume get(String uuid) {
        final List<Map<String, String>> queryResult = sqlHelper.executeQuery("Select * from resume WHERE uuid = ?",
                new String[]{uuid});
        if (queryResult.isEmpty()) {
            throw new NotExistStorageException(uuid);
        }
        return queryResultToResume(queryResult.get(0));
    }

    private Resume queryResultToResume(Map<String, String> queryResult) {
        return new Resume(
                queryResult.get("uuid").trim(),
                queryResult.get("full_name").trim()
        );
    }

    @Override
    public void delete(String uuid) {
        if (get(uuid) == null) {
            throw new NotExistStorageException(uuid);
        }
        sqlHelper.execute("DELETE FROM resume WHERE uuid = ?",
                new String[]{uuid});
    }

    @Override
    public List<Resume> getAllSorted() {
        final List<Map<String, String>> queryResult = sqlHelper.executeQuery("Select * from resume",
                EMPTY_PARAMS);
        return queryResult.stream()
                .map(this::queryResultToResume)
                .collect(Collectors.toList());
    }

    @Override
    public int size() {
        return getAllSorted().size();
    }
}
