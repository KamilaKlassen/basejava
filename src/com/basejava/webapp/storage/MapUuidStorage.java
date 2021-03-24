package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage {
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
    public boolean isExist(Object searchKey) {
        return searchKey != null;
    }

    @Override
    public void insert(Object searchKey, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void remove(Object searchKey) {
        mapStorage.remove((String) searchKey);
    }

    @Override
    public void renew(Object searchKey, Resume resume) {
        mapStorage.put((String) searchKey, resume);
    }

    @Override
    public Resume take(Object searchKey) {
        return mapStorage.get((String) searchKey);
    }

    @Override
    public Object getSearchKey(String searchKey) {
        return mapStorage.containsKey(searchKey) ? searchKey : null;
    }
}
