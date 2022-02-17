package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExperienceInformation implements ResumeSection {

    private List<Experiense> experienceList = new ArrayList<>();

    public List<Experiense> getExperienceList() {
        return experienceList;
    }

    public void setExperienceList(List<Experiense> experienceList) {
        this.experienceList = experienceList;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Experiense experiense : experienceList) {
            stringBuilder.append(experiense.getTitle());
            if (experiense.getPosition() != null) {
                stringBuilder.append(experiense.getPosition());
            }
            stringBuilder.append(experiense.getStartDate());
            stringBuilder.append(experiense.getEndDate());
            stringBuilder.append(experiense.getText());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public class Experiense {

        String title;
        String position;
        LocalDate startDate;
        LocalDate endDate;
        String text;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public void setStartDate(LocalDate startDate) {
            this.startDate = startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public void setEndDate(LocalDate endDate) {
            this.endDate = endDate;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
