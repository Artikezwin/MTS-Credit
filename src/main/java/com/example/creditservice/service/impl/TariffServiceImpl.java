package com.example.creditservice.service.impl;

import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.repository.TariffRepository;
import com.example.creditservice.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;

    @Override
    public List<Tariff> getTariffs() {
        return tariffRepository.findAll();
    }

    @Override
    public Tariff getById(long id) {
        return tariffRepository.findTariffById(id);
    }

    @Override
    public int save(Tariff tariff) {
        return tariffRepository.save(tariff);
    }

    @Override
    public Tariff delete(long id) {
        return tariffRepository.delete(id);
    }
}
