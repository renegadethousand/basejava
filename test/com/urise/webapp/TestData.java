package com.urise.webapp;

import com.urise.webapp.model.Resume;

import java.util.UUID;

public class TestData {

    public static String NAME_1 = "ALEXEI";
    public static String NAME_2 = "IVAN";
    public static String NAME_3 = "SERGEY";
    public static String NAME_4 = "VASILIY";
    public static String EMPTY_NAME = "EMPTY_NAME";

    public static final String UUID_1 = UUID.randomUUID().toString();
    public static final String UUID_2 = UUID.randomUUID().toString();
    public static final String UUID_3 = UUID.randomUUID().toString();
    public static final String UUID_4 = UUID.randomUUID().toString();

    public static final String NOT_EXIST_UUID = "dummy";

    public static final Resume R1;
    public static final Resume R2;
    public static final Resume R3;
    public static final Resume R4;
    public static final Resume RESUME_EXAMPLE_NOT_EXIST;

    static {
        R1 = ResumeTestData.generateResume(UUID_1, NAME_1);
        R2 = ResumeTestData.generateResume(UUID_2, NAME_2);
        R3 = ResumeTestData.generateResume(UUID_3, NAME_3);
        R4 = ResumeTestData.generateResume(UUID_4, NAME_4);
        RESUME_EXAMPLE_NOT_EXIST = ResumeTestData.generateResume(NOT_EXIST_UUID, EMPTY_NAME);
    }
}
