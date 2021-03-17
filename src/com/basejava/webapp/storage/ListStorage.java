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
    public void insert(Integer index, Resume resume) {
        resumeList.add(resume);
    }

    @Override
    public void remove(Integer index) {
        resumeList.remove((int)index);
    }

    @Override
    public void renew(Integer index, Resume resume) {
        resumeList.add(index,resume);
    }

    @Override
    public Resume take(Integer index) {
        return resumeList.get(index);
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

    public Integer getIndex(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
