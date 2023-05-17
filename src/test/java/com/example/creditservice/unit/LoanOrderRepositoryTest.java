package com.example.creditservice.unit;

import com.example.creditservice.config.PersistenceLayerTestConfig;
import com.example.creditservice.model.enums.OrderStatus;
import com.example.creditservice.model.order.LoanOrder;
import com.example.creditservice.repository.impl.LoanOrderRepositoryImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.sql.init.data-locations=classpath:data-test.sql"
})
@ContextConfiguration(classes = {
        PersistenceLayerTestConfig.class,
        LoanOrderRepositoryImpl.class
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)    // без этого не робит liquibase, использование создание бд с конфига (liquibase)
@EnableJpaRepositories(basePackages = {"ru.mts.credit_registration"})   // указываем директурию для сканирования бинов, которые описаны выше
@TestInstance(TestInstance.Lifecycle.PER_CLASS)     // Для BeforeAll нужен статический метод, что создает ограничения (использование только статических полей, бинов и тд)
// мы это ограничение обходим через управление жизненным циклом теста. Мы создадим только один экземпляр класса для теста и будем его использовать всегда
public class LoanOrderRepositoryTest {

    private static LoanOrder testOrder;
    @Autowired
    private LoanOrderRepositoryImpl loanOrderRepository;

    @BeforeAll
    public void init() {
        LoanOrder order = LoanOrder.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(1L)
                .tariffId(1L)
                .creditRating(0.4)
                .status(OrderStatus.APPROVED)
                .timeInsert(new Timestamp(System.currentTimeMillis()))
                .build();

        loanOrderRepository.save(order);
        testOrder = loanOrderRepository.findByUserIdAndOrderId(order.getUserId(), UUID.fromString(order.getOrderId())).orElseThrow();
    }

    @Test
    public void findOrdersByUserId() {
        Long userId = testOrder.getUserId();

        List<LoanOrder> actualOrders = loanOrderRepository.findByUserId(userId);

        assertThat(actualOrders.size()).isEqualTo(4);   // ищем все заявки user`a, их должно быть 4
    }

    @Test
    public void save() {
        String orderId = UUID.randomUUID().toString();
        LoanOrder expectedOrder = LoanOrder.builder()
                .orderId(orderId)
                .userId(1L)
                .tariffId(2L)
                .creditRating(0.62)
                .status(OrderStatus.REFUSED)
                .timeInsert(new Timestamp(System.currentTimeMillis()))
                .build();

        loanOrderRepository.save(expectedOrder);
        LoanOrder actualOrder = loanOrderRepository.findByUserIdAndOrderId(expectedOrder.getUserId(), UUID.fromString(expectedOrder.getOrderId())).orElseThrow();

        assertThat(actualOrder.getOrderId()).isEqualTo(expectedOrder.getOrderId());
        assertThat(actualOrder.getStatus()).isEqualTo(expectedOrder.getStatus());
        assertThat(actualOrder.getTimeInsert()).isEqualTo(expectedOrder.getTimeInsert());
    }

    @Test
    public void findStatusByOrderId() {
        OrderStatus expectedStatus = testOrder.getStatus();

        OrderStatus actualStatus = loanOrderRepository.findByUserIdAndOrderId(testOrder.getUserId(), UUID.fromString(testOrder.getOrderId())).get().getStatus();

        assertThat(actualStatus).isEqualTo(expectedStatus);
    }

    @Test
    public void deleteByUserIdAndOrderId() {
        String orderId = UUID.randomUUID().toString();
        Long userId = 1L;
        LoanOrder orderToSave = LoanOrder.builder()
                .orderId(orderId)
                .userId(userId)
                .tariffId(1L)
                .creditRating(0.4D)
                .status(OrderStatus.REFUSED)
                .timeInsert(new Timestamp(System.currentTimeMillis()))
                .build();


        loanOrderRepository.save(orderToSave);
        loanOrderRepository.deleteByUserIdAndOrderId(userId, UUID.fromString(orderId));
        Optional<LoanOrder> actualOrder =
                loanOrderRepository.findByUserIdAndOrderId(userId, UUID.fromString(orderId));

        assertThat(actualOrder).isEmpty();
    }

    @Test
    public void findByUserIdAndOrderId() {
        Long userId = testOrder.getUserId();
        String orderId = testOrder.getOrderId();
        Double creditRating = testOrder.getCreditRating();

        LoanOrder actualOrder =
                loanOrderRepository.findByUserIdAndOrderId(userId, UUID.fromString(orderId)).get();

        assertThat(actualOrder.getOrderId()).isEqualTo(orderId);
        assertThat(actualOrder.getUserId()).isEqualTo(userId);
        assertThat(actualOrder.getStatus()).isEqualTo(OrderStatus.APPROVED);
        assertThat(actualOrder.getCreditRating()).isEqualTo(creditRating);
    }

    @Test
    public void findByStatus() {
        OrderStatus status = OrderStatus.IN_PROGRESS;

        List<LoanOrder> actualOrders =
                loanOrderRepository.findByStatus(status);

        assertThat(actualOrders.size()).isEqualTo(1);
        assertThat(actualOrders.get(0).getStatus()).isEqualTo(status);
    }

    @Test
    public void updateStatusAndTimeUpdateById() {
        String orderId = testOrder.getOrderId();
        long id = testOrder.getId();
        long userId = testOrder.getUserId();
        OrderStatus expectedStatus = OrderStatus.APPROVED;
        Timestamp timeUpdate = new Timestamp(System.currentTimeMillis());

        loanOrderRepository.updateStatusByOrderId(expectedStatus, timeUpdate, id);
        LoanOrder expectedOrder = loanOrderRepository.findByUserIdAndOrderId(userId, UUID.fromString(orderId)).get();
        OrderStatus actualStatus = expectedOrder.getStatus();
        Timestamp actualTimeupdate = expectedOrder.getTimeUpdate();

        assertThat(actualTimeupdate).isEqualTo(timeUpdate);
        assertThat(actualStatus).isEqualTo(expectedStatus);
    }
}
