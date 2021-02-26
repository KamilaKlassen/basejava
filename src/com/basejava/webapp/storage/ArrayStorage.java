package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        int index = -1;
        for (int i = 0; i < actualSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
            }
        }
        return index;
    }

    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            storage[index] = storage[actualSize - 1];
            storage[actualSize - 1] = null;
            actualSize--;
        } else {
            System.out.println("The resume " + uuid + " does not exist!");
        }
    }

    public void save(Resume resume) {
        if (actualSize == STORAGE_LIMIT) {
            System.out.println("The storage is full!");
            return;
        }
        int index = getIndex(resume.getUuid());
        if (index >= 0) {
            System.out.println("The resume " + resume.getUuid() + " already exists!");
            return;
        }
        storage[actualSize] = resume;
        actualSize++;
    }
}
