package com.ioiDigital.TheCoffeeShop.entity;

public enum EStatusOrder {
    RECEIVED("Pending"),
    PREPARING("Processing"),
    DONE("Completed"),
    CANCEL("Cancelled");

    private final String statusOrder;

    EStatusOrder(String statusOrder) {
        this.statusOrder = statusOrder;
    }

    public String getStatusOrder() {
        return statusOrder;
    }
}
