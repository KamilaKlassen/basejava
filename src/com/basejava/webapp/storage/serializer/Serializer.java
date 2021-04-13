package com.basejava.webapp.storage.serializer;

import com.basejava.webapp.model.Resume;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {

    void write(Resume resume, OutputStream os) throws IOException;

    Resume read(InputStream is) throws IOException;
}
