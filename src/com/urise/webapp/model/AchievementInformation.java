package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class AchievementInformation implements ResumeSection {

    List<String> text = new ArrayList<>();

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
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
        for (String s : text) {
            System.out.println(s);
        }
    }
}
