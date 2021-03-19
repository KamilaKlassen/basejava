package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

public abstract class AbstractStorage implements Storage{

    @Override
    public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        remove(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return take(searchKey);
    }

    @Override
    public void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        renew(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        insert(searchKey, resume);

    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);

        }
        return searchKey;
    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    public abstract boolean isExist(Object searchKey);

    public abstract void insert(Object searchKey, Resume resume);

    public abstract void remove(Object searchKey);

    public abstract void renew(Object searchKey, Resume resume);

    public abstract Resume take(Object searchKey);

    public abstract Object getSearchKey(String uuid);
}

