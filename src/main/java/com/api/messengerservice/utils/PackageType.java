package com.api.messengerservice.utils;

public enum PackageType {

    LIGHTWEIGHT(30000,"Liviano"),
    MEDIUM(40000, "Mediano"),
    LARGE(50000, "Grande");

    private final double price;
    private final String nameSpanish;

    PackageType(double price, String nameSpanish) {
        this.price = price;
        this.nameSpanish = nameSpanish;
    }

    public double getPrice() {
        return price;
    }

    public String getNameSpanish() {
        return nameSpanish;
    }
}
