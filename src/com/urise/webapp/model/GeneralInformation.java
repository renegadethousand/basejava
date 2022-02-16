package com.urise.webapp.model;

import java.util.List;

public class GeneralInformation implements ResumeSection {

    String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String read() {
        return null;
    }

    @Override
    public void save() {

    }

    @Override
    public void print() {
        System.out.println(getText());
    }
}
