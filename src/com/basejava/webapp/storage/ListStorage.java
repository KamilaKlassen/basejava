package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;

public class ListStorage extends AbstractStorage {
    protected ArrayList<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public boolean isExist(Object index) {
        return index != null;
    }

    @Override
    public void insert(Object index, Resume resume) {
        resumeList.add(resume);
    }

    @Override
    public void remove(Object index) {
        resumeList.remove((int) index);
    }

    @Override
    public void renew(Object index, Resume resume) {
        resumeList.add((Integer) index,resume);
    }

    @Override
    public Resume take(Object index) {
        return resumeList.get((Integer) index);
    }

    @Override
    public int size() {
        return resumeList.size();
    }

    @Override
    public Resume[] getAll() {
        Resume[] resumes = new Resume[resumeList.size()];
        return resumeList.toArray(resumes);
    }

    public Object getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}
