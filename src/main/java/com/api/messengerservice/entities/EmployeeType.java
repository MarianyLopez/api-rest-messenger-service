package com.api.messengerservice.entities;

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
