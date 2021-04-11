package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {

    private File directory;
    Serializer serializer;

    protected FileStorage(File directory, Serializer serializer) {
        this.serializer = serializer;
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
        } catch (IOException e) {
            throw new StorageException("File insert error" + file.getAbsolutePath(), file.getName(), e);
        }
        renew(file, resume);
    }

    @Override
    public void remove(File file) {
        if (!file.delete()) {
            throw new StorageException("File delete error", file.getName());
        }
    }

    @Override
    public void renew(File file, Resume resume) {
        try {
            serializer.write(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File update error", file.getName(), e);
        }
    }

    @Override
    public Resume take(File file) {
        try {
            return serializer.read(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
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
