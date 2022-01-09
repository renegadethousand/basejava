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
    protected void saveResume(int index, Resume resume) {
        int insertionIndex = -index - 1;
        if (size > 0) {
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        }
        storage[insertionIndex] = resume;
    }
}
