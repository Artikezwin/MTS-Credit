package com.example.creditservice.service.impl;

import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.loan.order.CreateOrder;
import com.example.creditservice.model.loan.order.LoanOrder;
import com.example.creditservice.repository.LoanOrderRepository;
import com.example.creditservice.repository.TariffRepository;
import com.example.creditservice.service.LoanOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanOrderServiceImpl implements LoanOrderService {
    private final LoanOrderRepository loanOrderRepository;
    private final TariffRepository tariffRepository;

    @Override
    public List<LoanOrder> findByUserId(long userId) {
        List<LoanOrder> loanOrderList = loanOrderRepository.findByUserId(userId).orElseThrow();
        return loanOrderList;
    }

    @Override
    public int save(CreateOrder order) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setUserId(order.getUserId());
        loanOrder.setTariffId(order.getTariffId());

        if (tariffRepository.existsById(loanOrder.getTariffId())) {
            List<LoanOrder> loanOrderList = loanOrderRepository.findByUserId(loanOrder.getUserId()).orElseThrow();

            for (int i = 0; i < loanOrderList.size(); i++) {
                if (loanOrderList.get(i).getTariffId() == loanOrder.getTariffId()) {
                    switch (loanOrderList.get(i).getStatus()) {
                        case IN_PROGRESS:
                            throw new RuntimeException("LOAN_CONSIDERATION");
                        case APPROVED:
                            throw new RuntimeException("LOAN_ALREADY_APPROVED");
                        case REFUSED:
                            throw new RuntimeException("TRY_LATER");
                    }
                }
            }

            loanOrder.setOrderId(UUID.randomUUID().toString());
            loanOrder.setCreditRating(0.1 + Math.random() * 0.8);
            loanOrder.setStatus(OrderStatus.IN_PROGRESS);
            loanOrder.setTimeInsert(new Timestamp(System.currentTimeMillis()));
            return loanOrderRepository.save(loanOrder);
        } else {
            throw new RuntimeException("TARIFF_NOT_FOUND");
        }
    }

    @Override
    public String getStatusByOrderId(UUID orderId) {
        return loanOrderRepository.getStatusByOrderId(orderId).orElseThrow();
    }

    @Override
    public int deleteByOrderIdAndUserId(UUID orderId, long userId) {
        return deleteByOrderIdAndUserId(orderId, userId);
    }
}
