package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove(getUuid(searchKey));
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.put(getUuid(searchKey), resume);
    }

    @Override
    protected Object getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    private String getUuid(Object searchKey) {
        return ((Resume) searchKey).getUuid();
    }
}
