package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void clear();

    void update(Resume resume);

    void save(Resume resume);

    Resume get(String uuid);

    void delete(String uuid);

//    Resume[] getAll();
    // return list, sorted by name
     List<Resume> getAllSorted();

    int size();
}
