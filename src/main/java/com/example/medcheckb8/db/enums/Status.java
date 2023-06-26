package com.example.medcheckb8.db.enums;

public enum Status {
    CANCELED("Отменен"),
    CONFIRMED("Подтвержден"),
    COMPLETED("Завершено");
    private final String translate;
    Status(String translate) {
        this.translate = translate;
    }
    public String getTranslate() {
        return translate;
    }
}
