package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MapStorage extends AbstractStorage {

    private Map<String, Resume> storage = new HashMap();

    @Override
    protected int getIndex(String uuid) {
        int index = -1;
        Iterator<Map.Entry<String, Resume>> iterator = storage.entrySet().iterator();
        while (iterator.hasNext()) {
            index++;
            Map.Entry<String, Resume> entry = iterator.next();
            if (entry.getKey().equals(uuid)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    protected void saveResume(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void deleteResume(int index) {
        int currentIndex = -1;
        Iterator<Map.Entry<String, Resume>> iterator = storage.entrySet().iterator();
        while (iterator.hasNext()) {
            currentIndex++;
            iterator.next();
            if (currentIndex == index) {
                iterator.remove();
            }
        }
    }

    @Override
    protected Resume getResume(int index) {
        int currentIndex = -1;
        Iterator<Map.Entry<String, Resume>> iterator = storage.entrySet().iterator();
        while (iterator.hasNext()) {
            currentIndex++;
            Map.Entry<String, Resume> entry = iterator.next();
            if (currentIndex == index) {
                return entry.getValue();
            }
        }
        return null;
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        Object[] array = storage.values().toArray();
        return Arrays.copyOf(array, array.length, Resume[].class);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
