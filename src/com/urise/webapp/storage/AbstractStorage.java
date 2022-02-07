package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        Object searchKey = getSearchKeyAndThrowExceptionIfIsExist(resume.getUuid());
        saveResume(searchKey, resume);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getSearchKeyAndThrowExceptionIfIsNotExist(uuid);
        return getResume(searchKey);
    }

    @Override
    public void delete(String uuid) {
        Object searchKey = getSearchKeyAndThrowExceptionIfIsNotExist(uuid);
        deleteResume(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getSearchKeyAndThrowExceptionIfIsNotExist(resume.getUuid());
        updateResume(searchKey, resume);
    }

    public Object getSearchKeyAndThrowExceptionIfIsNotExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(searchKey.toString());
        }
        return searchKey;
    }

    public Object getSearchKeyAndThrowExceptionIfIsExist(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(searchKey.toString());
        }
        return searchKey;
    }

    protected abstract boolean isExist(Object searchKey);

    protected abstract void saveResume(Object searchKey, Resume resume);

    protected abstract void deleteResume(Object searchKey);

    protected abstract Resume getResume(Object searchKey);

    protected abstract void updateResume(Object searchKey, Resume resume);

    protected abstract Object getSearchKey(String uuid);
}
