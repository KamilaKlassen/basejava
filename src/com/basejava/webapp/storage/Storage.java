package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

public interface Storage {

    void delete(String uuid);

    int size();

    Resume get(String uuid);

    void update(Resume resume);

    void clear();

    void save(Resume resume);

    Resume[] getAll();


}
