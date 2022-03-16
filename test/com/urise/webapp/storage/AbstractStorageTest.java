package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected Storage storage;

    protected static String NAME_1 = "ALEXEI";
    protected static String NAME_2 = "IVAN";
    protected static String NAME_3 = "SERGEY";
    protected static String NAME_4 = "VASILIY";
    protected static String EMPTY_NAME = "EMPTY_NAME";

    private static final String UUID_1 = UUID.randomUUID().toString();
    private static final String UUID_2 = UUID.randomUUID().toString();
    private static final String UUID_3 = UUID.randomUUID().toString();
    private static final String UUID_4 = UUID.randomUUID().toString();

    private static final String NOT_EXIST_UUID = "dummy";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;
    private static final Resume RESUME_EXAMPLE_NOT_EXIST;


    static {
        R1 = ResumeTestData.generateResume(UUID_1, NAME_1);
        R2 = ResumeTestData.generateResume(UUID_2, NAME_2);
        R3 = ResumeTestData.generateResume(UUID_3, NAME_3);
        R4 = ResumeTestData.generateResume(UUID_4, NAME_4);
        RESUME_EXAMPLE_NOT_EXIST = ResumeTestData.generateResume(NOT_EXIST_UUID, EMPTY_NAME);
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(UUID_1));
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
        List<Resume> expected = List.of(R1, R2, R3);

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
        storage.update(R3);
        assertEquals(R3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_EXAMPLE_NOT_EXIST);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertEquals(R4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R3);
    }
}
