package com.example.creditservice.controller;

import com.example.creditservice.model.Answer;
import com.example.creditservice.model.DataResponseTariff;
import com.example.creditservice.model.loan.order.CreateOrder;
import com.example.creditservice.model.loan.order.DeleteOrder;
import com.example.creditservice.model.loan.order.LoanOrder;
import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.model.tariff.TariffDTO;
import com.example.creditservice.service.LoanOrderService;
import com.example.creditservice.service.TariffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loan-service")
@RequiredArgsConstructor
public class LoanOrderController {
    private final TariffService tariffService;
    private final LoanOrderService loanOrderService;

    @PostMapping("/addTariff")
    public ResponseEntity<Integer> addTariff(@RequestBody TariffDTO tariffDTO) {
        Tariff tariff = new Tariff();
        tariff.setType(tariffDTO.getType());
        tariff.setInterestRate(tariffDTO.getInterest_rate());
        return ResponseEntity.ok(tariffService.save(tariff));
    }

    @GetMapping("/getTariffs")
    public ResponseEntity<Answer> getTariffs() {
        return ResponseEntity.ok(
                new Answer(
                    new DataResponseTariff(
                            tariffService.getTariffs())));
    }

    @PostMapping("/order")
    public ResponseEntity<Integer> addOrder(@RequestBody CreateOrder order) {
        return ResponseEntity.ok(loanOrderService.save(order));
    }

    @DeleteMapping("/deleteOrder")
    public ResponseEntity<?> deleteOrder(@RequestBody DeleteOrder order) {
        return ResponseEntity.ok(null);
    }
}
