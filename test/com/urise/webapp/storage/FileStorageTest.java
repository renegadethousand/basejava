package com.urise.webapp.storage;

import com.urise.webapp.storage.serializy.ObjectStreamSerializeStrategy;

public class FileStorageTest extends AbstractStorageTest {
    public FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializeStrategy()));
    }
}
