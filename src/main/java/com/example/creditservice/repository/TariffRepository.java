package com.example.creditservice.repository;

import com.example.creditservice.model.tariff.Tariff;

import java.util.List;

public interface TariffRepository {
    List<Tariff> findAll();
    Tariff findTariffById(long id);
    int save(Tariff tariff);
    Tariff delete(long id);
}
