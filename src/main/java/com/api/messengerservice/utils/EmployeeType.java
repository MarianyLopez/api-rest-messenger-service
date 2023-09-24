package com.api.messengerservice.utils;

public enum EmployeeType {

    DRIVER("Conductor"),
    DISTRIBUTOR("Repartidor"),
    COORDINATOR("coordinador");

    private final String nameSpanish;

    EmployeeType(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    public String getNameSpanish() {
        return nameSpanish;
    }
}
