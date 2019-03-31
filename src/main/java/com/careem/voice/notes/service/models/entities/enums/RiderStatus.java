package com.careem.voice.notes.service.models.entities.enums;

public enum RiderStatus {
    WAITING("Waiting"),
    ON_BOARD("On Board");

    private final String full;

    RiderStatus(String full) {
        this.full = full;
    }

    public String getFullName() {
        return full;
    }
}
