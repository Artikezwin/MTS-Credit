package com.example.creditservice.service.impl;

import com.example.creditservice.exception.TimeOutException;
import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.model.tariff.TariffDTO;
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
        return tariffRepository.findAll().orElseThrow();
    }

    @Override
    public int save(TariffDTO tariffDTO) {
        Tariff tariff = new Tariff();
        tariff.setType(tariffDTO.getType());
        tariff.setInterestRate(tariffDTO.getInterest_rate());
        return tariffRepository.save(tariff);
    }

    @Override
    public int delete(long id) {
        return tariffRepository.delete(id);
    }

    @Override
    public List<Tariff> getTariffsFallback(final Throwable t) {
        throw new TimeOutException("REQUEST_TIME_OUT: GET_TARIFFS", "Не удалось получить тарифы. Превышено время ожидания");
    }
}
