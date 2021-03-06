package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "EMPTY");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveResumeToStorage(int index, Resume resume) {
        int insertionIndex = -index - 1;
        if (size > 0) {
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        }
        storage[insertionIndex] = resume;
    }

    @Override
    protected void deleteResumeFromStorage(int index) {
        if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

}
