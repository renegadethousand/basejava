package com.urise.webapp.model;

import java.util.List;

public class GeneralInformation implements ResumeSection {

    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
