package com.example.creditservice.service;

import com.example.creditservice.model.tariff.Tariff;

import java.util.List;

public interface TariffService {
    List<Tariff> getTariffs();
    Tariff getById(long id);
    int save(Tariff tariff);
    Tariff delete(long id);
}
