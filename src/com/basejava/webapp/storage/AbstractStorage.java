package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    @Override
    public void delete(String uuid) {
        Integer index = getExistingIndex(uuid);
        remove(index);
    }

    @Override
    public Resume get(String uuid) {
        Integer index = getExistingIndex(uuid);
        return take(index);
    }

    @Override
    public void update(Resume resume) {
        Integer index = getExistingIndex(resume.getUuid());
        renew(index, resume);
    }

    @Override
    public void save(Resume resume) {
        Integer index = getNotExistingIndex(resume.getUuid());
        insert(index, resume);

    }

    private Integer getNotExistingIndex(String uuid) {
        Integer index = getIndex(uuid);
        if (index >= 0) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private Integer getExistingIndex(String uuid) {
        Integer index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        return index;
    }

    @Override
    public abstract Resume[] getAll();

    @Override
    public abstract int size();

    @Override
    public abstract void clear();

    public abstract void insert(Integer index, Resume resume);

    public abstract void remove(Integer index);

    public abstract void renew(Integer index, Resume resume);

    public abstract Resume take(Integer index);

    public abstract Integer getIndex(String uuid);
}
