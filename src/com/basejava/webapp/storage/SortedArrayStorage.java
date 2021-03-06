package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, actualSize, searchKey);
    }

    @Override
    protected void insert(Resume resume, int index) {
        int i = -index - 1;
        System.arraycopy(storage, i, storage, i + 1, actualSize - i);
        storage[i] = resume;
    }
}

