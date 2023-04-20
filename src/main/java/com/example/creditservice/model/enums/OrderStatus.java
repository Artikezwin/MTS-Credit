package com.example.creditservice.model.enums;

public enum OrderStatus {
    IN_PROGRESS("IN_PROGRESS"),
    APPROVED("APPROVED"),
    REFUSED("REFUSED");

    private final String orderStatusName;
    OrderStatus(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }
}
