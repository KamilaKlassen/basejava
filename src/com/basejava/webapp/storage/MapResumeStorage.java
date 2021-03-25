package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {

    protected Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    protected List<Resume> getList() {
        return new ArrayList<>(mapStorage.values());
    }

    @Override
    public int size() {
        return mapStorage.size();
    }

    @Override
    public void clear() {
        mapStorage.clear();
    }

    @Override
    public boolean isExist(Resume searchKey) {
        return searchKey != null;
    }

    @Override
    public void insert(Resume searchKey, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void remove(Resume searchKey) {
        mapStorage.remove(searchKey.getUuid());
    }

    @Override
    public void renew(Resume searchKey, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume take(Resume searchKey) {
        return searchKey;
    }

    @Override
    public Resume getSearchKey(String searchKey) {
        return mapStorage.get(searchKey);
    }
}
