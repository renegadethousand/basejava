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
    protected void saveResume(int index, Resume resume) {
        int insertionIndex = Math.abs(index + 1);
        storage[insertionIndex] = resume;
    }
}
