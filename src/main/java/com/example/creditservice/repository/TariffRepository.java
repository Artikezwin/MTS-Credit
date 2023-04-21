package com.example.creditservice.repository;

import com.example.creditservice.model.tariff.Tariff;

import java.util.List;

public interface TariffRepository {
    List<Tariff> findAll();
    Boolean existsById(long tariffId);
    int save(Tariff tariff);
    Tariff delete(long id);
}
