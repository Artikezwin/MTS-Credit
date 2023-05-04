package com.example.creditservice.service;

import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.model.tariff.TariffDTO;

import java.util.List;

public interface TariffService {
    List<Tariff> getTariffs();
    int save(TariffDTO tariffDTO);
    Tariff delete(long id);

    List<Tariff> getTariffsFallback(final Throwable t);
}
