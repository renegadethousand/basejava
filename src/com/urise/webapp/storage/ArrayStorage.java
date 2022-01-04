package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10_000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public void save(Resume resume) {
        if (size < storage.length) {
            if (findResumeIndex(resume.getUuid()) == -1) {
                storage[size++] = resume;
            } else {
                System.out.println("Резюме с uuid " + resume.getUuid() + "уже есть в базе!");
            }
        } else {
            System.out.println("Закончилось место в массиве!");
        }
    }

    public Resume get(String uuid) {
        int index = findResumeIndex(uuid);
        if (index > -1) {
            System.out.println("Резюме с uuid " + uuid + " найдено в базе!");
            return storage[index];
        } else {
            System.out.println("Резюме с uuid " + uuid + " ненайдено в базе!");
            return null;
        }
    }

    public void delete(String uuid) {
        int index = findResumeIndex(uuid);
        if (index > -1) {
            size--;
            if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
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
        int index = findResumeIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            System.out.println("Резюме с uuid " + resume.getUuid() + " в базе ненайдено!");
        }
    }
}
