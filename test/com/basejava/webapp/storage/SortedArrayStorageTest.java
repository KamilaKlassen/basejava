package com.basejava.webapp.storage;

import com.basejava.webapp.model.Resume;
import org.junit.Before;

public class SortedArrayStorageTest extends AbstractArrayStorageTest {
    private SortedArrayStorage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    public SortedArrayStorageTest(SortedArrayStorage storage) {
        super(storage);
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }
}
