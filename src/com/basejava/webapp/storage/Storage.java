package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.List;

public interface Storage {

    void delete(String uuid);

    int size();

    Resume get(String uuid);

    void update(Resume resume);

    void clear();

    void save(Resume resume);

    List<Resume> getAllSorted();
}
