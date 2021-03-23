package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    public Object getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid, "Z");
        return Arrays.binarySearch(storage, 0, actualSize, searchKey,
                Comparator.comparing(Resume::getUuid));
    }

    @Override
    public void insertElement(Object index, Resume resume) {
        int i = -(Integer) index - 1;
        System.arraycopy(storage, i, storage, i + 1, actualSize - i);
        storage[i] = resume;
    }
}

