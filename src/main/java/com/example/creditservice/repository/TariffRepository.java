package com.example.creditservice.repository;

import com.example.creditservice.model.tariff.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffRepository {
    List<Tariff> findAll();
    Boolean existsById(long tariffId);
    Boolean existsByType(String type);
    int save(Tariff tariff);
    int delete(long id);
}
