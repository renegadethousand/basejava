package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage implements Storage {

    protected abstract boolean isExist(Object searchKey);

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract Object getSearchKey(String uuid);

    protected abstract Resume[] getAll();

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistSearchKey(resume.getUuid());
        doSave(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistSearchKey(uuid);
        doDelete(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = Arrays.asList(getAll());
        resumes.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return resumes;
    }

    public Object getExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public Object getNotExistSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }
}
