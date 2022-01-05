package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{
    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index > -1) {
            storage[index] = resume;
        } else {
            System.out.println("Резюме с uuid " + resume.getUuid() + " в базе ненайдено!");
        }
    }

    @Override
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Закончилось место в массиве!");
            return;
        }

        int index = Arrays.binarySearch(storage, 0, size, resume);

        if (index < 0) {
            storage[Math.abs(index + 1)] = resume;
            size++;
        } else {
            System.out.println("Резюме с uuid " + resume.getUuid() + "уже есть в базе!");
        }
    }
}
