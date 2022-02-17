package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class AchievementInformation implements ResumeSection {

    private List<String> text = new ArrayList<>();

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : text) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }
}
