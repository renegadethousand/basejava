package com.urise.webapp.storage;

import com.urise.webapp.Config;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {
    public SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }
}
