package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static String EMPTY_NAME = "EMPTY";

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String NOT_EXIST_UUID = "dummy";

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1, EMPTY_NAME));
        storage.save(new Resume(UUID_2, EMPTY_NAME));
        storage.save(new Resume(UUID_3, EMPTY_NAME));
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(new Resume(UUID_1, EMPTY_NAME), storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(NOT_EXIST_UUID);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = List.of(
                new Resume(UUID_1, EMPTY_NAME),
                new Resume(UUID_2, EMPTY_NAME),
                new Resume(UUID_3, EMPTY_NAME));

        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(NOT_EXIST_UUID);
    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_3, EMPTY_NAME);
        storage.update(resume);
        assertEquals(resume, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        Resume resume = new Resume(NOT_EXIST_UUID, EMPTY_NAME);
        storage.update(resume);
    }

    @Test
    public void save() {
        Resume resume = new Resume(UUID_4, EMPTY_NAME);
        storage.save(resume);
        assertEquals(resume, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        Resume resume = new Resume(UUID_3, EMPTY_NAME);
        storage.save(resume);
    }
}
