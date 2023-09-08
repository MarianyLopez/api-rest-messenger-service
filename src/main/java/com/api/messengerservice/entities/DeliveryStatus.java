package com.api.messengerservice.entities;

public enum DeliveryStatus {
    RECIBED("Recibido"),
    ONROUTE("En ruta"),
    DELIVERED("Entregado");

    private final String nameSpanish;

    DeliveryStatus(String nameSpanish) {
        this.nameSpanish = nameSpanish;
    }
}
