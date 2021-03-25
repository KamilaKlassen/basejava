package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends AbstractStorage<String> {
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
    public boolean isExist(String searchKey) {
        return searchKey != null;
    }

    @Override
    public void insert(String searchKey, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void remove(String searchKey) {
        mapStorage.remove(searchKey);
    }

    @Override
    public void renew(String searchKey, Resume resume) {
        mapStorage.put(searchKey, resume);
    }

    @Override
    public Resume take(String searchKey) {
        return mapStorage.get(searchKey);
    }

    @Override
    public String getSearchKey(String searchKey) {
        return mapStorage.containsKey(searchKey) ? searchKey : null;
    }
}
