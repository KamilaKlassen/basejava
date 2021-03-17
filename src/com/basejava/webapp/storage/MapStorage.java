package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    protected Map<String, Resume> mapStorage = new LinkedHashMap<>();

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[mapStorage.size()];
        return mapStorage.values().toArray(resumes);
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
    public boolean isExist(Object index) {
        return index != null;
    }

    @Override
    public void insert(Object index, Resume resume) {
        mapStorage.put(resume.getUuid(), resume);
    }

    @Override
    public void remove(Object key) {
        mapStorage.remove((String) key);
    }

    @Override
    public void renew(Object key, Resume resume) {
        mapStorage.put((String) key, resume);
    }

    @Override
    public Resume take(Object key) {
        return mapStorage.get((String) key);
    }

    @Override
    public Object getIndex(String key) {
        for(Map.Entry<String, Resume> entry : mapStorage.entrySet()){
            if(entry.getKey().equals(key)){
                return entry.getKey();
            }
        }
        return null;
    }
}
