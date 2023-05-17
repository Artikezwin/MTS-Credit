package com.example.creditservice.util;

import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.order.LoanOrder;
import com.example.creditservice.repository.LoanOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
@Component
public class Scheduler {
    private final int fixedRate = 1000 * 2 * 60;  // а здесь в той ситуации мы кладем операцию в стек
    private final int fixedDelay = 1000 * 2 * 60;   // если метод не успевает отработать за 2 минуты, мы просто ждём
    private final int initialDelay = 1000 * 5;
    private final LoanOrderRepository loanOrderRepository;

    @Scheduled(fixedDelay = fixedDelay, initialDelay = initialDelay)
    public void considerationApplication() {
        List<LoanOrder> loanOrderList = loanOrderRepository.findByStatus(OrderStatus.IN_PROGRESS);
        for (LoanOrder loanOrder : loanOrderList) {
            if (new Random().nextBoolean()) {
                loanOrderRepository.updateStatusByOrderId(
                        OrderStatus.APPROVED,
                        new Timestamp(System.currentTimeMillis()),
                        loanOrder.getId()
                );
            } else {
                loanOrderRepository.updateStatusByOrderId(
                        OrderStatus.REFUSED,
                        new Timestamp(System.currentTimeMillis()),
                        loanOrder.getId()
                );
            }
        }
    }
}
