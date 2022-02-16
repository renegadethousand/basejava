package com.urise.webapp.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpirienceInformation implements ResumeSection {

    List<Expiriense> expirienseList = new ArrayList<>();

    public List<Expiriense> getExpirienseList() {
        return expirienseList;
    }

    public void setExpirienseList(List<Expiriense> expirienseList) {
        this.expirienseList = expirienseList;
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
        for (Expiriense expiriense : expirienseList) {
            System.out.println(expiriense.getTitle());
            if (expiriense.getPosition() != null) {
                System.out.println(expiriense.getPosition());
            }
            System.out.println(expiriense.getStartDate());
            System.out.println(expiriense.getEndDate());
            System.out.println(expiriense.getText());
            System.out.println();
        }
    }

    public class Expiriense {

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
