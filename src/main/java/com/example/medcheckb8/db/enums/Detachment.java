package com.example.medcheckb8.db.enums;

public enum Detachment {
    ALLERGOLOGY("Аллергология"),
    ANESTHESIOLOGY("Анестезиология"),
    VACCINATION("Вакцинация"),
    GASTROENTEROLOGY("Гастроэнтерология"),
    GYNECOLOGY("Гинекология"),
    DERMATOLOGY("Дерматология"),
    CARDIOLOGY("Кардиология"),
    NEUROLOGY("Неврология"),
    NEUROSURGERY("Нейрохирургия"),
    ONCOLOGY("Онкология"),
    ORTHOPEDICS("Ортопедия"),
    OTORHINOLARYNGOLOGY("Отоларингология"),
    OPHTHALMOLOGY("Офтальмология"),
    PROCTOLOGY("Проктология"),
    PSYCHOTHERAPY("Психотерапия"),
    PULMONOLOGY("Пульмонология"),
    RHEUMATOLOGY("Ревматология"),
    THERAPY("Терапия"),
    UROLOGY("Урология"),
    PHLEBOLOGY("Флебология"),
    PHYSIOTHERAPY("Физиотерапия"),
    ENDOCRINOLOGY("Эндокринология");

    private final String translate;

    Detachment(String translate) {
        this.translate = translate;
    }

    public String getTranslate() {
        return translate;
    }
}
