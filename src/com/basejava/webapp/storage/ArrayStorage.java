package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int actualSize = 0;

    public void update(Resume r) {
        if (actualSize != 0) {
            for (int i = 0; i < actualSize; i++) {
                if (storage[i].equals(r)) {
                    storage[i] = r;
                    return;
                }
            }
            System.out.println("This resume doesn't exist!");
        } else {
            System.out.println("The storage is empty!");
        }
    }

    public void clear() {
        Arrays.fill(storage, 0, actualSize, null);
        actualSize = 0;
    }

    public void save(Resume r) {
        if (actualSize == storage.length) {
            System.out.println("The storage is full!");
            return;
        } else {
            for (int i = 0; i < actualSize; i++) {
                if (storage[i].equals(r)) {
                    System.out.println("This resume already exists!");
                    return;
                }
            }
        }
        storage[actualSize] = r;
        actualSize++;
    }

    public Resume get(String uuid) {
        Resume resume = null;
        if (actualSize != 0) {
            for (int i = 0; i < actualSize; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    resume = storage[i];
                } else {
                    System.out.println("This resume doesn't exist!");
                }
                break;
            }
        } else {
            System.out.println("The storage is empty!");
        }
        return resume;
    }

    public void delete(String uuid) {
        if (actualSize != 0) {
            for (int i = 0; i < actualSize; i++) {
                if (storage[i].getUuid().equals(uuid)) {
                    storage[i] = storage[actualSize - 1];
                    storage[actualSize - 1] = null;
                    actualSize--;
                } else {
                    System.out.println("This resume doesn't exist!");
                }
                break;
            }
        } else {
            System.out.println("The storage is empty!");
        }
    }

    public Resume[] getAll() {
        return Arrays.copyOfRange(storage, 0, actualSize);
    }

    public int size() {
        return actualSize;
    }
}
