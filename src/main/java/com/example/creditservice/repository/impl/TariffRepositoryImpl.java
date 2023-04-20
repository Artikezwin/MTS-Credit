package com.example.creditservice.repository.impl;

import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.repository.TariffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class TariffRepositoryImpl implements TariffRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String SELECT_ALL_FROM_TABLE = "select * from TARIFF";
    private final String SELECT_FROM_TABLE_WHERE_ID = "select from TARIFF where ID = ?";
    private final String INSERT_INTO_TABLE = "insert into TARIFF (TYPE, INTEREST_RATE) values (?, ?)";
    private final String DELETE_FROM_TABLE = "delete from TARIFF where ID = ?";

    @Autowired
    public TariffRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<Tariff> findAll() {
        return jdbcTemplate.query(
                SELECT_ALL_FROM_TABLE,
                new BeanPropertyRowMapper<>(Tariff.class));
    }

    @Override
    public Tariff findTariffById(long id) {
        return jdbcTemplate.queryForObject(
                SELECT_FROM_TABLE_WHERE_ID,
                Tariff.class,
                id
        );
    }

    @Override
    public int save(Tariff tariff) {
        return jdbcTemplate.update(
                INSERT_INTO_TABLE,
                tariff.getType(),
                tariff.getInterestRate());
    }

    @Override
    public Tariff delete(long id) {
        Tariff tariff = findTariffById(id);
        jdbcTemplate.update(
                DELETE_FROM_TABLE,
                id
        );
        return tariff;
    }

    private Tariff mapToTariff(ResultSet row, int rowNum) throws SQLException {
        return new Tariff(
                row.getLong("ID"),
                row.getString("TYPE"),
                row.getString("INTEREST_RATE")
        );
    }
}
