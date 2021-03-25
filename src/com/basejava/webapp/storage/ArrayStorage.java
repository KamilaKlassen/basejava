package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    public Integer getSearchKey(String uuid) {
        int index = -1;
        for (int i = 0; i < actualSize; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
            }
        }
        return index;
    }

    @Override
    public void insertElement(Integer index, Resume resume) {
        storage[actualSize] = resume;
    }
}
