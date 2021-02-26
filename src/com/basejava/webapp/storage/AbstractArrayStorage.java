package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualSize = 0;

    protected abstract int getIndex(String uuid);

    public abstract void delete(String uuid);

    public abstract void save(Resume resume);

    public int size() {
        return actualSize;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("The resume " + uuid + " does not exist!");
            return null;
        }
        return storage[index];
    }

    public void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("The resume " + resume.getUuid() + " does not exist!");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, actualSize, null);
        actualSize = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, actualSize);
    }
}
