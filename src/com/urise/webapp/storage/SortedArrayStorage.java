package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Empty");
        return Arrays.binarySearch(storage, 0, size, searchKey);
    }

    @Override
    protected void saveElement(Object searchKey, Resume resume) {
        int index = (Integer) searchKey;
        int insertionIndex = -index - 1;
        if (size > 0) {
            System.arraycopy(storage, insertionIndex, storage, insertionIndex + 1, size - insertionIndex);
        }
        storage[insertionIndex] = resume;
    }

    @Override
    protected void deleteElement(int index) {
        if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
    }

}
