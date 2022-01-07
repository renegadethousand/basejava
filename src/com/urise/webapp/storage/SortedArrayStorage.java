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
    public void save(Resume resume) {
        if (size == STORAGE_LIMIT) {
            System.out.println("Закончилось место в массиве!");
            return;
        }

        int index = getIndex(resume.getUuid());

        if (index >= 0) {
            System.out.println("Резюме с uuid " + resume.getUuid() + "уже есть в базе!");
        } else {
            int insertionIndex = Math.abs(index + 1);
            if (size > 0) {
                System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
            }
            storage[insertionIndex] = resume;
            size++;
        }
    }
}
