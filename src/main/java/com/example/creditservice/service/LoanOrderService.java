package com.example.creditservice.service;

import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.loan.order.CreateOrder;
import com.example.creditservice.model.loan.order.LoanOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanOrderService {
    List<LoanOrder> findByUserId(long userId);
    UUID save(CreateOrder order);
    OrderStatus getStatusByOrderId(UUID orderId);
    int deleteByOrderIdAndUserId(UUID orderId, long userId);
}
