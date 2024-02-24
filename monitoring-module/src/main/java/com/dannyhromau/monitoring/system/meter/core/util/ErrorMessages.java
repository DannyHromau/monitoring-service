package com.dannyhromau.monitoring.system.meter.core.util;

public enum ErrorMessages {
    DUPLICATED_DATA_MESSAGE("The given data exists"),
    NO_ACTUAL_DATA_MESSAGE("No actual data"),
    ENTITY_NOT_FOUND_MESSAGE("Entity with %s: %s not exists"),
    WRONG_VALUE_MESSAGE("Wrong meter reading value: %s"),
    WRONG_INPUT_FORMAT_MESSAGE("Wrong input format"),
    WRONG_AUTH_MESSAGE("Wrong login or password");

    public final String label;

    ErrorMessages(String label) {
        this.label = label;
    }
}
