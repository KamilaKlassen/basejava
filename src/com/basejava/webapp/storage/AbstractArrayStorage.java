package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int actualSize = 0;

    public final void save(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (actualSize == STORAGE_LIMIT) {
            System.out.println("The storage is full!");
            return;
        }
        if (index >= 0) {
            System.out.println("The resume " + resume.getUuid() + " already exists!");
            return;
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
            System.out.println("The resume " + uuid + " does not exist!");
            return null;
        }
        return storage[index];
    }

    public final void update(Resume resume) {
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        } else {
            System.out.println("The resume " + resume.getUuid() + " does not exist!");
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
            System.out.println("The resume " + uuid + " does not exist!");
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
