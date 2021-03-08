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

    public void save(Resume resume) {
        String foundUuid = resume.getUuid();
        if (actualSize == STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", foundUuid);
        }
        int index = getIndex(foundUuid);
        if (index >= 0) {
            throw new ExistStorageException(foundUuid);
        }
        insert(resume, index);
        actualSize++;
    }

    public int size() {
        return actualSize;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            throw new NotExistStorageException(uuid);
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            throw new NotExistStorageException(resume.getUuid());
        }
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            if (actualSize - (index + 1) >= 0)
                System.arraycopy(storage, index + 1, storage, index + 1 - 1, actualSize - (index + 1));
            storage[actualSize - 1] = null;
            actualSize--;
        } else {
            throw new NotExistStorageException(uuid);
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, actualSize, null);
        actualSize = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage,actualSize);
    }

    protected abstract int getIndex(String uuid);

    protected abstract void insert(Resume resume, int index);
}
