package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void save(Resume resume) {
        checkArraySize();
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(index, resume);
    }

    @Override
    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            return getElement(index);
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            fillDeletedElement(index);
            decreaseArray();
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            updateElement(index, resume);
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    protected abstract int getIndex(String uuid);

    protected abstract void saveResume(int index, Resume resume);

    protected abstract void fillDeletedElement(int index);

    protected abstract void checkArraySize();

    protected abstract void decreaseArray();

    protected abstract Resume getElement(int index);

    protected abstract void updateElement(int index, Resume resume);
}
