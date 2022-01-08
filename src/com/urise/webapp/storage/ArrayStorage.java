package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -(size + 1);
    }

    @Override
    protected void prepareArray(int insertionIndex) {
    }

    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Закончилось место в массиве!");
            return;
        }

        int index = getIndex(resume.getUuid());

        if (index >= 0) {
            System.out.println("Резюме с uuid " + resume.getUuid() + "уже есть в базе!");
        } else {
            storage[Math.abs(index + 1)] = resume;
            size++;
        }
    }
}
