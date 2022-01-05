package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 100_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            System.out.println("Резюме с uuid " + uuid + " найдено в базе!");
            return storage[index];
        } else {
            System.out.println("Резюме с uuid " + uuid + " ненайдено в базе!");
            return null;
        }
    }

    protected abstract int getIndex(String uuid);

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index > -1) {
            size--;
            if (size - 1 - index >= 0) System.arraycopy(storage, index + 1, storage, index, size - 1 - index);
        } else {
            System.out.println("Элемент "  + uuid + " в базе не найден!");
        }
    }
}