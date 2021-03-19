package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualSize = 0;

    @Override
    public void remove(Object index) {
        Integer id = (Integer) index;
        if (actualSize - (id + 1) >= 0)
            System.arraycopy(storage, id + 1, storage, id, actualSize - (id + 1));
        storage[actualSize - 1] = null;
        actualSize--;
    }

    @Override
    public void renew(Object index, Resume resume) {
        storage[(Integer) index] = resume;
    }

    @Override
    public Resume take(Object index) {
        return storage[(Integer) index];
    }

    public int size() {
        return actualSize;
    }

    public void clear() {
        Arrays.fill(storage, 0, actualSize, null);
        actualSize = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, actualSize);
    }

    @Override
    public void insert(Object index, Resume resume) {
        if (actualSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement((Integer)index, resume);
            actualSize++;
        }
    }

    @Override
    public boolean isExist(Object index) {
        return (Integer)index >= 0;
    }

    @Override
    public abstract Object getSearchKey(String uuid);

    public abstract void insertElement(Object index, Resume resume);
}
