package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private static final int STORAGE_LIMIT = 10000;

    private Resume[] storage = new Resume[STORAGE_LIMIT];
    private int actualSize = 0;

    private int findIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < actualSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
            }
        }
        return index;
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
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

    public void save(Resume resume) {
        if (actualSize == STORAGE_LIMIT) {
            System.out.println("The storage is full!");
            return;
        }
        int index = findIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("The resume " + resume.getUuid() + " already exists!");
            return;
        }
        storage[actualSize] = resume;
        actualSize++;
    }

    public Resume get(String uuid) {
        Resume resume = null;
        int index = findIndex(uuid);
        if (index >= 0) {
            resume = storage[index];
        } else {
            System.out.println("The resume " + uuid + " does not exist!");
        }
        return resume;
    }

    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[actualSize - 1];
            storage[actualSize - 1] = null;
            actualSize--;
        } else {
            System.out.println("The resume " + uuid + " does not exist!");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, actualSize);
    }

    public int size() {
        return actualSize;
    }
}
