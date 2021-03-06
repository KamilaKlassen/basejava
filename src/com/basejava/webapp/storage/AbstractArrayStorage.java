package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualSize = 0;

    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (actualSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        if (index >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        insert(resume, index);
        actualSize++;
    }

    public final int size() {
        return actualSize;
    }

    public final Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public final void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for (int i = index + 1; i < actualSize; i++) {
                storage[i - 1] = storage[i];
            }
            storage[actualSize - 1] = null;
            actualSize--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public final void clear() {
        Arrays.fill(storage, 0, actualSize, null);
        actualSize = 0;
    }

    public final Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, actualSize);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int index);
}
