package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {

    protected static String EMPTY_NAME = "EMPTY";

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";
    private static final String NOT_EXIST_UUID = "dummy";

    private static final Resume RESUME_EXAMPLE_1 = ResumeTestData.generateResume(UUID_1, EMPTY_NAME);
    private static final Resume RESUME_EXAMPLE_2 = ResumeTestData.generateResume(UUID_2, EMPTY_NAME);
    private static final Resume RESUME_EXAMPLE_3 = ResumeTestData.generateResume(UUID_3, EMPTY_NAME);
    private static final Resume RESUME_EXAMPLE_4 = ResumeTestData.generateResume(UUID_4, EMPTY_NAME);
    private static final Resume RESUME_EXAMPLE_NOT_EXIST = ResumeTestData.generateResume(NOT_EXIST_UUID, EMPTY_NAME);

    protected Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(RESUME_EXAMPLE_1);
        storage.save(RESUME_EXAMPLE_2);
        storage.save(RESUME_EXAMPLE_3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(RESUME_EXAMPLE_1, storage.get(UUID_1));
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
                RESUME_EXAMPLE_1,
                RESUME_EXAMPLE_2,
                RESUME_EXAMPLE_3);

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
        storage.update(RESUME_EXAMPLE_3);
        assertSame(RESUME_EXAMPLE_3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_EXAMPLE_NOT_EXIST);
    }

    @Test
    public void save() {
        storage.save(RESUME_EXAMPLE_4);
        assertEquals(RESUME_EXAMPLE_4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(RESUME_EXAMPLE_3);
    }
}
