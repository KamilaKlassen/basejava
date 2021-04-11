package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualSize = 0;

    @Override
    public void remove(Integer index) {
        if (actualSize - (index + 1) >= 0)
            System.arraycopy(storage, index + 1, storage, index, actualSize - (index + 1));
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

    @Override
    public int size() {
        return actualSize;
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, actualSize, null);
        actualSize = 0;
    }

    @Override
    protected List<Resume> getAll() {
        return Arrays.asList(Arrays.copyOfRange(storage, 0, actualSize));
    }

    @Override
    public void insert(Integer index, Resume resume) {
        if (actualSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        } else {
            insertElement(index, resume);
            actualSize++;
        }
    }

    @Override
    public boolean isExist(Integer index) {
        return index >= 0;
    }

    @Override
    public abstract Integer getSearchKey(String uuid);

    public abstract void insertElement(Integer index, Resume resume);
}
