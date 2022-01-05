package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Закончилось место в массиве!");
        } else if (getIndex(resume.getUuid()) == -1) {
            storage[size++] = resume;
        } else {
            System.out.println("Резюме с uuid " + resume.getUuid() + "уже есть в базе!");
        }
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            System.out.println("Резюме с uuid " + resume.getUuid() + " в базе ненайдено!");
        }
    }
}
