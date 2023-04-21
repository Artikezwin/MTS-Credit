package com.example.creditservice.repository.impl;

import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.loan.order.LoanOrder;
import com.example.creditservice.repository.LoanOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class LoanOrderRepositoryImpl implements LoanOrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String SELECT_ALL_FROM_TABLE = "select * from LOAN_ORDER";
    private final String SELECT_FROM_TABLE_WHERE_USER_ID = "select * from LOAN_ORDER where USER_ID = ?";
    private final String SELECT_FROM_TABLE_WHERE_ORDER_ID_AND_USER_ID = "select * from LOAN_ORDER where ORDER_ID = ? AND USER_ID = ?";
    private final String SELECT_FROM_TABLE_WHERE_ORDER_ID = "select * from LOAN_ORDER where ORDER_ID = ?";
    private final String INSERT_INTO_TABLE = "insert into LOAN_ORDER (ORDER_ID, USER_ID, TARIFF_ID, CREDIT_RATING, STATUS, TIME_INSERT) values (?, ?, ?, ?, ?, ?)";
    private final String DELETE_BY_ORDER_ID_AND_USER_ID = "delete from LOAN_ORDER where ORDER_ID = ? AND USER_ID = ?";

    @Autowired
    public LoanOrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<List<LoanOrder>> findByUserId(long userId) {
        return Optional.of(
                jdbcTemplate.query(
                        SELECT_FROM_TABLE_WHERE_USER_ID,
                        new BeanPropertyRowMapper<>(LoanOrder.class),
                        userId
                )
        );
    }

//    public Optional<LoanOrder> findByOrderIdAndUserId(UUID orderId, long userId) {
//        return Optional.of(
//                jdbcTemplate.queryForObject(
//                        SELECT_FROM_TABLE_WHERE_ORDER_ID_AND_USER_ID,
//                        LoanOrder.class,
//                        orderId,
//                        userId
//                )
//        );
//    }

    @Override
    public UUID save(LoanOrder loanOrder) {
        jdbcTemplate.update(
                INSERT_INTO_TABLE,
                loanOrder.getOrderId(),
                loanOrder.getUserId(),
                loanOrder.getTariffId(),
                loanOrder.getCreditRating(),
                loanOrder.getStatus().toString(),
                loanOrder.getTimeInsert()
        );
        return UUID.fromString(loanOrder.getOrderId());
    }

    @Override
    public Optional<OrderStatus> getStatusByOrderId(UUID orderId) {
        return Optional.ofNullable(
                jdbcTemplate.queryForObject(
                        SELECT_FROM_TABLE_WHERE_ORDER_ID,
                        new BeanPropertyRowMapper<>(LoanOrder.class),
                        orderId.toString()
                ).getStatus()
        );
    }

    @Override
    public int deleteByOrderIdAndUserId(UUID orderId, long userId) {
        return jdbcTemplate.update(
                DELETE_BY_ORDER_ID_AND_USER_ID,
                orderId,
                userId
        );
    }


}
