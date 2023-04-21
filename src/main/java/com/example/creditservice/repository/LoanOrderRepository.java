package com.example.creditservice.repository;

import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.loan.order.LoanOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanOrderRepository {
    Optional<List<LoanOrder>> findByUserId(long userId);
    UUID save(LoanOrder loanOrder);
    Optional<OrderStatus> getStatusByOrderId(UUID orderId);
    int deleteByOrderIdAndUserId(UUID orderId, long userId);
}
