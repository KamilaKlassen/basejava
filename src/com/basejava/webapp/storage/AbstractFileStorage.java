package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "Directory cannot be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    protected List<Resume> getList() {
        File[] files = directory.listFiles();
        if (files == null) {
            throw new StorageException("Directory is empty", null);
        }
        List<Resume> list = new ArrayList<>();
        for (File file : files) {
            list.add(take(file));
        }
        return list;
    }

    @Override
    public boolean isExist(File file) {
        return file.exists();
    }

    @Override
    public void insert(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("File insert error", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume r, File file) throws IOException;

    @Override
    public void remove(File file) {
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    remove(f);
                } else {
                    if (!f.delete()) {
                        throw new StorageException("The file could not be deleted", f.getName());
                    }
                }
            }
            if (!file.delete()) {
                throw new StorageException("The file could not be deleted", file.getName());
            }
        }
    }

    @Override
    public void renew(File file, Resume resume) {
        try {
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("File update error", file.getName(), e);
        }
    }

    @Override
    public Resume take(File file) {
        Resume resume;
        try (FileInputStream fis = new FileInputStream(file.getPath());
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            resume = (Resume) ois.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            throw new StorageException("File read error", file.getName(), ex);
        }
        return resume;
    }

    @Override
    public File getSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    public int size() {
        String[] list = directory.list();
        if (list == null) {
            throw new StorageException("Directory is empty", null);
        }
        return list.length;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File f : files) {
                remove(f);
            }
        }
    }
}
