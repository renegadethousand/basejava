package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public abstract class AbstractArrayStorageTest {

    private Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String NOT_EXIST_UUID = "dummy";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size() {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        Assert.assertEquals(new Resume(UUID_1), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(NOT_EXIST_UUID);
    }

    @Test
    public void clear() {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] all = storage.getAll();
        Assert.assertEquals(all[0], storage.get(UUID_1));
        Assert.assertEquals(all[1], storage.get(UUID_2));
        Assert.assertEquals(all[2], storage.get(UUID_3));
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void delete() {
        storage.delete(UUID_1);
        Assert.assertEquals(2, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(NOT_EXIST_UUID);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_3);
        storage.update(resume);
        assertEquals(storage.get(UUID_3), resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(NOT_EXIST_UUID);
        storage.update(resume);
    }

    @Test
    public void save() {
        Resume resume = new Resume(UUID_4);
        storage.save(resume);
        assertEquals(resume, storage.get(UUID_4));
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume resume = new Resume(UUID_3);
        storage.save(resume);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 0; i < 9997; i++) {
                storage.save(new Resume());
            }
        } catch (StorageException exception) {
            fail("Исключение было выброшено раньше времени");
        }
        storage.save(new Resume());
    }
}