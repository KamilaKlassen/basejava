package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    @Override
    public void delete(String uuid) {
        Object index = getExistingIndex(uuid);
        remove(index);
    }

    @Override
    public Resume get(String uuid) {
        Object index = getExistingIndex(uuid);
        return take(index);
    }

    @Override
    public void update(Resume resume) {
        Object index = getExistingIndex(resume.getUuid());
        renew(index, resume);
    }

    @Override
    public void save(Resume resume) {
        Object index = getNotExistingIndex(resume.getUuid());
        insert(index, resume);

    }

    private Object getNotExistingIndex(String uuid) {
        Object index = getIndex(uuid);
        if (isExist(index)) {
            throw new ExistStorageException(uuid);
        }
        return index;
    }

    private Object getExistingIndex(String uuid) {
        Object index = getIndex(uuid);
        if (!isExist(index)) {
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

    public abstract boolean isExist(Object index);

    public abstract void insert(Object index, Resume resume);

    public abstract void remove(Object index);

    public abstract void renew(Object index, Resume resume);

    public abstract Resume take(Object index);

    public abstract Object getIndex(String uuid);
}
