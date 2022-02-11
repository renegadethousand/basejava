package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    protected abstract void saveElement(Object searchKey, Resume resume);

    protected abstract void deleteElement(int searchKey);

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
    protected void doSave(Object searchKey, Resume resume) {
        checkArraySize();
        saveElement(searchKey, resume);
        size++;
    }

    @Override
    protected void doDelete(Object searchKey) {
        deleteElement((int) searchKey);
        storage[size - 1] = null;
        size--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected void checkArraySize() {
        if (size == STORAGE_LIMIT) {
            throw new StorageException("Закончилось место в массиве!", "");
        }
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return (int) searchKey >= 0;
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }
}
