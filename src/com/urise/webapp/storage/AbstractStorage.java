package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        if (isExist(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    @Override
    public Resume get(String uuid) {
        checkResumeNotExist(new Resume(uuid));
        return getResume(uuid);
    }

    @Override
    public void delete(String uuid) {
        checkResumeNotExist(new Resume(uuid));
        deleteResume(uuid);
    }

    @Override
    public void update(Resume resume) {
        checkResumeNotExist(resume);
        updateResume(resume);
    }

    public void checkResumeNotExist(Resume resume) {
        if (!isExist(resume)) {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    protected abstract boolean isExist(Resume resume);

    protected abstract void saveResume(Resume resume);

    protected abstract void deleteResume(String uuid);

    protected abstract Resume getResume(String uuid);

    protected abstract void updateResume(Resume resume);
}
