package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage {

    protected Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>(mapStorage.values());
        list.sort(Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid));
        return list;
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
        mapStorage.remove(((Resume) searchKey).getUuid());
    }

    @Override
    public void renew(Object searchKey, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public Resume take(Object searchKey) {
        return (Resume) searchKey;
    }

    @Override
    public Object getSearchKey(String searchKey) {
        return mapStorage.get(searchKey);
    }
}
