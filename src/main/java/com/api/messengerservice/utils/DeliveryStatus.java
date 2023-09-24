package com.api.messengerservice.utils;

public enum DeliveryStatus {
    RECIBED("Recibido"),
    ONROUTE("En ruta"),
    DELIVERED("Entregado");

    private final String nameSpanish;

    DeliveryStatus(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }

    public String getNameSpanish() {
        return nameSpanish;
    }
}
