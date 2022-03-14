package com.urise.webapp.storage;

import com.urise.webapp.Config;

import java.util.Properties;

public class SqlStorageTest extends AbstractStorageTest {
    private static final Properties props = Config.get().getProps();

    public SqlStorageTest() {
        super(new SqlStorage(new SqlHelper(props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password"))));
    }
}
