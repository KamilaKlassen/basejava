package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage<Integer> {
    private List<Resume> resumeList = new ArrayList<>();

    @Override
    public void clear() {
        resumeList.clear();
    }

    @Override
    public boolean isExist(Integer index) {
        return index != null;
    }

    @Override
    public void insert(Integer index, Resume resume) {
        resumeList.add(resume);
    }

    @Override
    public void remove(Integer index) {
        resumeList.remove(index.intValue());
    }

    @Override
    public void renew(Integer index, Resume resume) {
        resumeList.add(index, resume);
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
    protected List<Resume> getList() {
        return new ArrayList<>(resumeList);
    }

    public Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeList.size(); i++) {
            if (resumeList.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }
        return null;
    }
}
