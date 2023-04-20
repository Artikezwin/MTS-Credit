package com.example.creditservice.model.loan.order;

import com.example.creditservice.model.enums.OrderStatus;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class LoanOrder {
    private long id;
    private String orderId;
    private long userId;
    private long tariffId;
    private double creditRating;
    private OrderStatus status;
    private Timestamp timeInsert;
    private Timestamp timeUpdate;
}
