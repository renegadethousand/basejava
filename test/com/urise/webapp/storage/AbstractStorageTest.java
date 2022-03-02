package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.ListSection;
import com.urise.webapp.model.Organization;
import com.urise.webapp.model.OrganizationSection;
import com.urise.webapp.model.Resume;
import com.urise.webapp.model.SectionType;
import com.urise.webapp.model.TextSection;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = new File("D:\\javaops\\basejava\\basejava\\storage");

    protected Storage storage;

    protected static String EMPTY_NAME = "EMPTY";

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    private static final String NOT_EXIST_UUID = "dummy";

    private static final Resume R1;
    private static final Resume R2;
    private static final Resume R3;
    private static final Resume R4;
    private static final Resume RESUME_EXAMPLE_NOT_EXIST;


    static {
        R1 = ResumeTestData.generateResume(UUID_1, EMPTY_NAME);
        R2 = ResumeTestData.generateResume(UUID_2, EMPTY_NAME);
        R3 = ResumeTestData.generateResume(UUID_3, EMPTY_NAME);
        R4 = ResumeTestData.generateResume(UUID_4, EMPTY_NAME);
        RESUME_EXAMPLE_NOT_EXIST = ResumeTestData.generateResume(NOT_EXIST_UUID, EMPTY_NAME);

        R1.addContact(ContactType.MAIL, "mail1@ya.ru");
        R1.addContact(ContactType.PHONE, "11111");
        R1.addSection(SectionType.OBJECTIVE, new TextSection("Objective1"));
        R1.addSection(SectionType.PERSONAL, new TextSection("Personal data"));
        R1.addSection(SectionType.ACHIEVEMENT, new ListSection("Achivment11", "Achivment12", "Achivment13"));
        R1.addSection(SectionType.QUALIFICATIONS, new ListSection("Java", "SQL", "JavaScript"));
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization11", "http://Organization11.ru",
                                new Organization.Position(2005, Month.JANUARY, "position1", "content1"),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "position2", "content2"))));
        R1.addSection(SectionType.EDUCATION,
                new OrganizationSection(
                        new Organization("Institute", null,
                                new Organization.Position(1996, Month.JANUARY, 2000, Month.DECEMBER, "aspirant", null),
                                new Organization.Position(2001, Month.MARCH, 2005, Month.JANUARY, "student", "IT facultet")),
                        new Organization("Organization12", "http://Organization12.ru")));
        R2.addContact(ContactType.SKYPE, "skype2");
        R2.addContact(ContactType.PHONE, "22222");
        R1.addSection(SectionType.EXPERIENCE,
                new OrganizationSection(
                        new Organization("Organization2", "http://Organization2.ru",
                                new Organization.Position(2015, Month.JANUARY, "position1", "content1"))));
    }

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @Before
    public void setUp() {
        storage.clear();
        storage.save(R1);
        storage.save(R2);
        storage.save(R3);
    }

    @Test
    public void size() {
        assertEquals(3, storage.size());
    }

    @Test
    public void get() {
        assertEquals(R1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(NOT_EXIST_UUID);
    }

    @Test
    public void clear() {
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    public void getAllSorted() {
        List<Resume> expected = List.of(R1, R2, R3);

        List<Resume> actual = storage.getAllSorted();
        assertEquals(expected, actual);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() {
        storage.delete(UUID_1);
        assertEquals(2, storage.size());
        storage.get(UUID_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() {
        storage.delete(NOT_EXIST_UUID);
    }

    @Test
    public void update() {
        storage.update(R3);
        assertEquals(R3, storage.get(UUID_3));
    }

    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() {
        storage.update(RESUME_EXAMPLE_NOT_EXIST);
    }

    @Test
    public void save() {
        storage.save(R4);
        assertEquals(R4, storage.get(UUID_4));
        assertEquals(4, storage.size());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() {
        storage.save(R3);
    }
}
