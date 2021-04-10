package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectStorage {

    void write(Resume resume, OutputStream os) throws IOException;

    Resume read(InputStream is) throws IOException;
}
