package com.example.creditservice.model.loan.order;

import lombok.Data;

import java.util.UUID;

@Data
public class DeleteOrder {
    private long userId;
    private UUID orderId;
}
