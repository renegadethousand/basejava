package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(Exception exception) {
        super(exception);
    }

    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}
