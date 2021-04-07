package com.basejava.webapp.storage;

import com.basejava.webapp.exception.ExistStorageException;
import com.basejava.webapp.exception.NotExistStorageException;
import com.basejava.webapp.model.*;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public abstract class AbstractStorageTest {
    protected Storage storage;

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;

    static {
        RESUME_1 = new Resume(UUID_1, "A");
        RESUME_2 = new Resume(UUID_2, "B");
        RESUME_3 = new Resume(UUID_3, "C");

        RESUME_1.addContact(ContactType.MOBILE, "0176 46372838");

        RESUME_1.addSection(SectionType.PERSONAL, new TextSection("Personal"));
        RESUME_1.addSection(SectionType.OBJECTIVE, new TextSection("Objective"));

        RESUME_1.addSection(SectionType.ACHIEVEMENT, new ListSection(Arrays.asList("Achievement 1", "Achievement 2")));
        RESUME_1.addSection(SectionType.QUALIFICATIONS, new ListSection(Arrays.asList("Qualification 1", "Qualification 2")));

        RESUME_1.addSection(SectionType.EXPERIENCE, new OrganizationSection(Arrays.asList(
                new Experience("Name", "URL", Arrays.asList(
                        new Position(LocalDate.of(2010, Month.JANUARY, 1), LocalDate.of(2012, Month.JANUARY, 1),
                                "Title", "Description"),
                        new Position(LocalDate.of(2015, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 1),
                                "Title", "Description")
                )))));
        RESUME_1.addSection(SectionType.EDUCATION, new OrganizationSection(Arrays.asList(
                new Experience("Name", "URL", Arrays.asList(
                        new Position(LocalDate.of(2010, Month.JANUARY, 1), LocalDate.of(2012, Month.JANUARY, 1),
                                "Title", "Description"),
                        new Position(LocalDate.of(2015, Month.JANUARY, 1), LocalDate.of(2019, Month.JANUARY, 1),
                                "Title", "Description")
                )),
                new Experience("Name 2", "URL", Arrays.asList(
                        new Position(LocalDate.of(2010, Month.JANUARY, 1), LocalDate.of(2012, Month.JANUARY, 1),
                                "Title", "Description"))))));
    }

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        assertSize(3);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void update() throws Exception {
        Resume resume = new Resume(UUID_3, "C");
        storage.update(resume);
        assertGet(resume);
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(new Resume("D"));
    }

    @Test
    public void getAll() throws Exception {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(RESUME_1, list.get(0));
        assertEquals(RESUME_2, list.get(1));
        assertEquals(RESUME_3, list.get(2));
    }

    @Test
    public void save() throws Exception {
        Resume resume = new Resume("E");
        storage.save(resume);
        assertNotNull(storage.get(resume.getUuid()));
        assertSize(4);
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID_1);
        assertSize(2);
        storage.get(UUID_1);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

    private void assertGet(Resume r) {
        assertEquals(r, storage.get(r.getUuid()));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }
}
