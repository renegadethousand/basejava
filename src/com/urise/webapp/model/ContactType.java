package com.urise.webapp.model;

public enum ContactType {

    PHONE("Телефон"),
    MAIL("Электронная почта"),
    SKYPE("SKYPE"),
    LINKEDIN("Профиль LinkedIn"),
    GITHUB("Профиль GitHub"),
    STACKOVERFLOW("Профиль stackoverflow"),
    HOMEPAGE("Домашняя страница");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
