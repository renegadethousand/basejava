package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.fail;

public abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() {
        try {
            for (int i = 4; i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
                storage.save(new Resume(UUID.randomUUID().toString(), Resume.EMPTY_NAME));
            }
        } catch (StorageException exception) {
            fail("Исключение было выброшено раньше времени");
        }
        storage.save(new Resume(UUID.randomUUID().toString(), Resume.EMPTY_NAME));
    }
}