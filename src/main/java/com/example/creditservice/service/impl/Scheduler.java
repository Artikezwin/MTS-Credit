package com.example.creditservice.service.impl;

import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.loan.order.LoanOrder;
import com.example.creditservice.repository.LoanOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@EnableAsync
@Service
public class Scheduler {
    private final int fixedRate = 1000 * 2 * 60;
    private final int initialDelay = 1000 * 5;
    private final LoanOrderRepository loanOrderRepository;

    @Async
    @Scheduled(fixedRate = fixedRate, initialDelay = initialDelay)
    public void considerationApplication() {
        List<LoanOrder> loanOrderList = loanOrderRepository.findByStatus(OrderStatus.IN_PROGRESS).orElseThrow();
        for (LoanOrder loanOrder : loanOrderList) {
            if (new Random().nextBoolean()) {
                loanOrderRepository.updateStatusByOrderId(
                        OrderStatus.APPROVED,
                        loanOrder.getId()
                );
            } else {
                loanOrderRepository.updateStatusByOrderId(
                        OrderStatus.REFUSED,
                        loanOrder.getId()
                );
            }
        }
    }
}
