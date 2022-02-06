package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListStorage extends AbstractStorage {

    private List<Resume> storage = new ArrayList<>();

    @Override
    protected boolean isExist(Resume resume) {
        return storage.contains(resume);
    }

    @Override
    protected void saveResume(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void deleteResume(String uuid) {
        storage.remove(new Resume(uuid));
    }

    @Override
    protected Resume getResume(String uuid) {
        return storage.get(storage.indexOf(new Resume(uuid)));
    }

    @Override
    protected void updateResume(Resume resume) {
        storage.set(storage.indexOf(new Resume(resume.getUuid())), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Object[] array = storage.toArray();
        return Arrays.copyOf(array, array.length, Resume[].class);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
