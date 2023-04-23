package com.example.creditservice.repository;

import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.loan.order.LoanOrder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoanOrderRepository {
    Optional<List<LoanOrder>> findByUserId(long userId);
    Optional<LoanOrder> findByUserIdAndOrderId(long userId, UUID orderId);
    UUID save(LoanOrder loanOrder);
    Optional<OrderStatus> getStatusByOrderId(UUID orderId);
    int deleteByUserIdAndOrderId(long userId, UUID orderId);
}
