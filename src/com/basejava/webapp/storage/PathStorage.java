package com.basejava.webapp.storage;

import com.basejava.webapp.exception.StorageException;
import com.basejava.webapp.model.Resume;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PathStorage extends AbstractStorage<Path> {

    private Path directory;
    Serializer serializer;

    protected PathStorage(String dir, Serializer serializer) {
        this.serializer = serializer;
        directory = Paths.get(dir);
        Objects.requireNonNull(directory, "directory must not be null");
        if (!Files.isDirectory(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(dir + " is not directory or is not writable");
        }
    }

    @Override
    protected List<Resume> getList() {
        String[] paths = directory.toFile().list();
        if (paths == null) {
            throw new StorageException("Directory read error ", null);
        }
        List<Resume> list = new ArrayList<>();
        for (String path : paths) {
            list.add(take(Path.of(path)));
        }
        return list;
    }

    @Override
    public boolean isExist(Path path) {
        return Files.exists(path);
    }

    @Override
    public void insert(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("Couldn't create Path " + path.getFileName(), path.toString(), e);
        }
        renew(path, resume);
    }

    @Override
    public void remove(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Path delete error " + path.getFileName(), path.toString(), e);
        }
    }

    @Override
    public void renew(Path path, Resume resume) {
        try {
            serializer.write(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path write error ", resume.getUuid(), e);
        }
    }

    @Override
    public Resume take(Path path) {
        try {
            return serializer.read(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("Path read error " + path.getFileName(), path.toString(), e);
        }
    }

    @Override
    public Path getSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    public int size() {
        String[] list = directory.toFile().list();
        if (list == null) {
            throw new StorageException("Directory read error ", null);
        }
        return list.length;
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::remove);
        } catch (IOException e) {
            throw new StorageException("Path delete error ", null);
        }
    }
}
