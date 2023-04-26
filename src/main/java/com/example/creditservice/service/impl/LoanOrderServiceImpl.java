package com.example.creditservice.service.impl;

import com.example.creditservice.exception.CustomException;
import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.loan.order.CreateOrder;
import com.example.creditservice.model.loan.order.LoanOrder;
import com.example.creditservice.repository.LoanOrderRepository;
import com.example.creditservice.repository.TariffRepository;
import com.example.creditservice.service.LoanOrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoanOrderServiceImpl implements LoanOrderService {
    private final LoanOrderRepository loanOrderRepository;
    private final TariffRepository tariffRepository;

    @Override
    public List<LoanOrder> findByUserId(long userId) {
        return loanOrderRepository.findByUserId(userId).orElseThrow();
    }

    @CircuitBreaker(name = "loan-order-service", fallbackMethod = "saveFallBack")
    @Override
    public UUID save(CreateOrder order) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setUserId(order.getUserId());
        loanOrder.setTariffId(order.getTariffId());

        if (tariffRepository.existsById(loanOrder.getTariffId())) {
            List<LoanOrder> loanOrderList = loanOrderRepository.findByUserId(loanOrder.getUserId()).orElseThrow();

            for (int i = 0; i < loanOrderList.size(); i++) {
                if (loanOrderList.get(i).getTariffId() == loanOrder.getTariffId()) {
                    switch (loanOrderList.get(i).getStatus()) {
                        case IN_PROGRESS -> {
                            log.error("LOAN_CONSIDERATION");
                            throw new CustomException("LOAN_CONSIDERATION", "Заявка на рассмотрении");
                        }
                        case APPROVED -> {
                            log.error("LOAN_ALREADY_APPROVED");
                            throw new CustomException("LOAN_ALREADY_APPROVED", "Заявка уже одобрена");
                        }
                        case REFUSED -> {
                            long now = new Timestamp(System.currentTimeMillis()).getTime();
                            if (now - loanOrderList.get(i).getTimeUpdate().getTime() < 1_000 * 2 * 60) {
                                log.error("TRY_LATER");
                                throw new CustomException("TRY_LATER", "Попробуйте позже");
                            }
                        }
                    }
                }
            }

            loanOrder.setOrderId(UUID.randomUUID().toString());
            loanOrder.setCreditRating(0.1 + Math.random() * 0.8);
            loanOrder.setStatus(OrderStatus.IN_PROGRESS);
            loanOrder.setTimeInsert(new Timestamp(System.currentTimeMillis()));
            return loanOrderRepository.save(loanOrder).orElseThrow();
        } else {
            log.error("TARIFF_NOT_FOUND");
            throw new CustomException("TARIFF_NOT_FOUND", "Тариф не найден");
        }
    }

    public UUID saveFallBack(final Throwable t) {
        log.error("Exception: " +  t.getMessage());
        throw new CustomException("SAVING_ERROR", "Ошибка сохранения заявки. Превышено время ожидания");
    }

    @CircuitBreaker(name = "loan-order-service", fallbackMethod = "getStatusByOrderIdFallback")
    @Override
    public OrderStatus getStatusByOrderId(UUID orderId) {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
            throw new RuntimeException();
        }

        return loanOrderRepository.getStatusByOrderId(orderId).orElseThrow(() -> new CustomException("TARIFF_NOT_FOUND", "Тариф не найден"));
    }

    public OrderStatus getStatusByOrderIdFallback(final Throwable t) {
        log.error("Exception: " + t.getMessage());
        return OrderStatus.IN_PROGRESS;
//        throw new CustomException("GETTING_STATUS_ERROR", "Ошибка получения статуса. Превышено время ожидания");
    }

    @CircuitBreaker(name = "loan-order-service", fallbackMethod = "deleteByOrderIdAndUserIdFallBack")
    @Override
    public int deleteByOrderIdAndUserId(long userId, UUID orderId) {
        LoanOrder loanOrder = loanOrderRepository.findByUserIdAndOrderId(userId, orderId).orElseThrow(() -> new CustomException("ORDER_NOT_FOUND", "Заявка не найдена"));
        if (loanOrder.getStatus() == OrderStatus.IN_PROGRESS) {
            return loanOrderRepository.deleteByUserIdAndOrderId(userId, orderId);
        }
        throw new CustomException("ORDER_IMPOSSIBLE_TO_DELETE", "Невозможно удалить заявку");
    }

    public int deleteByOrderIdAndUserIdFallBack(final Throwable t) {
        throw new CustomException("DELETE_ERROR", "Ошибка удаления. Превышено время ожидания");
    }
}
