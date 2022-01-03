package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[3];
    private int size = 0;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size < storage.length) {
            if (findResumeIndex(resume.getUuid()) == -1) {
                storage[size++] = resume;
            } else {
                System.out.println("Такое резюме уже есть в базе!");
            }
        } else {
            System.out.println("Закончилось место в массиве!");
        }
    }

    public Resume get(String uuid) {
        int resumeIndex = findResumeIndex(uuid);
        return resumeIndex > -1 ? storage[resumeIndex] : null;
    }

    public void delete(String uuid) {
        int resumeIndex = findResumeIndex(uuid);
        if (resumeIndex > -1) {
            size--;
            for (int i = resumeIndex; i < size - 1; i++) {
                storage[i] = storage[i + 1];
            }
        } else {
            System.out.println("Элемент "  + uuid + " в базе не найден!");
        }
    }

    private int findResumeIndex(String uuid) {
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
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    public void update(Resume resume) {
        int resumeIndex = findResumeIndex(resume.getUuid());
        if (resumeIndex > -1) {
            storage[resumeIndex] = resume;
        } else {
            System.out.println("Резюме с uuid " + resume.getUuid() + " в базе ненайдено!");
        }
    }
}
