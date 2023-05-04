package com.example.creditservice.service.impl;

import com.example.creditservice.exception.TimeOutException;
import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.model.tariff.TariffDTO;
import com.example.creditservice.repository.TariffRepository;
import com.example.creditservice.service.TariffService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TariffServiceImpl implements TariffService {
    private final TariffRepository tariffRepository;

    @CircuitBreaker(name = "loan-order-service", fallbackMethod = "getTariffsFallback")
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
    public Tariff delete(long id) {
        return tariffRepository.delete(id).orElseThrow();
    }

    @Override
    public List<Tariff> getTariffsFallback(final Throwable t) {
        throw new TimeOutException("REQUEST_TIME_OUT: GET_TARIFFS", "Не удалось получить тарифы. Превышено время ожидания");
    }
}
