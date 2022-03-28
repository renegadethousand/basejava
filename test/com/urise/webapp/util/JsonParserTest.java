package com.urise.webapp.util;

import com.urise.webapp.model.Resume;
import com.urise.webapp.model.Section;
import com.urise.webapp.model.TextSection;
import org.junit.Assert;
import org.junit.Test;

import static com.urise.webapp.TestData.R1;
import static org.junit.Assert.*;

public class JsonParserTest {

    @Test
    public void read() {
        String json = JsonParser.write(R1);
        System.out.println(json);
        Resume resume = JsonParser.read(json, Resume.class);
        Assert.assertEquals(R1, resume);
    }

    @Test
    public void write() {
        Section expected = new TextSection("Objective1");
        String json = JsonParser.write(expected, Section.class);
        System.out.println(json);
        final Section actual = JsonParser.read(json, Section.class);
        Assert.assertEquals(expected, actual);
    }
}