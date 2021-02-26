package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, actualSize, searchKey);
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index >= 0) {
            for(int i = index + 1; i < actualSize; i++){
                storage[i - 1] = storage[i];
            }
            storage[actualSize - 1] = null;
            actualSize--;
        } else {
            System.out.println("The resume " + uuid + " does not exist!");
        }
    }

    @Override
    public void save(Resume resume) {

    }
}
