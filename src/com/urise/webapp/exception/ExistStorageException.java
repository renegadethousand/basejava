package com.urise.webapp.exception;

public class ExistStorageException extends StorageException {

    public ExistStorageException(Exception exception) {
        this(exception.getMessage());
    }

    public ExistStorageException(String uuid) {
        super("Resume " + uuid + " already exist", uuid);
    }
}
