package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualSize = 0;

    @Override
    public void remove(Integer index) {
        if (actualSize - (index + 1) >= 0)
            System.arraycopy(storage, index + 1, storage, index + 1 - 1, actualSize - (index + 1));
        storage[actualSize - 1] = null;
        actualSize--;
    }

    @Override
    public void renew(Integer index, Resume resume) {
        storage[index] = resume;
    }

    @Override
    public Resume take(Integer index) {
        return storage[index];
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
    public void insert(Integer index, Resume resume) {
        if (actualSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(index,resume);
        }
    }

    @Override
    public abstract Integer getIndex(String uuid);

    public abstract void insertElement(Integer index, Resume resume);
}
