package com.example.creditservice.unit;

import com.example.creditservice.config.PersistenceLayerTestConfig;
import com.example.creditservice.model.tariff.Tariff;
import com.example.creditservice.repository.impl.TariffRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:data-test.sql"
})
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        TariffRepositoryImpl.class,
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@EnableJpaRepositories(basePackages = {"com.example.creditservice"})
public class TariffRepositoryTest {

    @Autowired
    private TariffRepositoryImpl tariffRepository;

    @Test
    public void findAll() {
        List<Tariff> actualTariffs = tariffRepository.findAll();

        assertThat(actualTariffs.size()).isEqualTo(3);
    }

    @Test
    public void willReturnTrueWhenExistById() {
        long tariffId = 1L;

        boolean isExists = tariffRepository.existsById(tariffId);

        assertThat(isExists).isTrue();
    }

    @Test
    public void willReturnFalseWhenExistById() {
        long falseId = -1L;

        boolean isExists = tariffRepository.existsById(falseId);

        Assertions.assertThat(isExists).isFalse();
    }
}