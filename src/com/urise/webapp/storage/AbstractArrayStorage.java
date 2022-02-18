package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void saveResumeToStorage(int searchKey, Resume resume);

    protected abstract void deleteResumeFromStorage(int searchKey);

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    @Override
    protected void doSave(Integer searchKey, Resume resume) {
        checkArraySize();
        saveResumeToStorage(searchKey, resume);
        size++;
    }

    private void checkArraySize() {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Закончилось место в массиве!", "");
        }
    }

    @Override
    protected void doDelete(Integer searchKey) {
        deleteResumeFromStorage(searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }
}
