package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

public abstract class AbstractStorage<S> implements Storage {

    @Override
    public void delete(String uuid) {
        S searchKey = getExistingSearchKey(uuid);
        remove(searchKey);
    }

    @Override
    public Resume get(String uuid) {
        S searchKey = getExistingSearchKey(uuid);
        return take(searchKey);
    }

    @Override
    public void update(Resume resume) {
        S searchKey = getExistingSearchKey(resume.getUuid());
        renew(searchKey, resume);
    }

    @Override
    public void save(Resume resume) {
        S searchKey = getNotExistingSearchKey(resume.getUuid());
        insert(searchKey, resume);
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        list.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return list;
    }

    private S getNotExistingSearchKey(String uuid) {
        S searchKey = getSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);

        }
        return searchKey;
    }

    private S getExistingSearchKey(String uuid) {
        S searchKey = getSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract List<Resume> getList();

    public abstract boolean isExist(S searchKey);

    public abstract void insert(S searchKey, Resume resume);

    public abstract void remove(S searchKey);

    public abstract void renew(S searchKey, Resume resume);

    public abstract Resume take(S searchKey);

    public abstract S getSearchKey(String uuid);
}

