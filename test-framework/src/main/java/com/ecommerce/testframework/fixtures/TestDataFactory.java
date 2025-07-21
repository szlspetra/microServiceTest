package com.ecommerce.testframework.fixtures;

import com.ecommerce.order.model.Order;
import com.ecommerce.payment.model.Payment;
import com.github.javafaker.Faker;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

public class TestDataFactory {

    private static final Faker faker = new Faker(new Random(42)); // Fixed seed for reproducibility

    public static Order createValidOrder() {
        return new Order(
            faker.internet().emailAddress(),
            new BigDecimal(faker.number().randomDouble(2, 10, 500)),
            "PENDING"
        );
    }

    public static Order createOrderWithEmail(String email) {
        return new Order(
            email,
            new BigDecimal(faker.number().randomDouble(2, 10, 500)),
            "PENDING"
        );
    }

    public static Order createOrderWithAmount(BigDecimal amount) {
        return new Order(
            faker.internet().emailAddress(),
            amount,
            "PENDING"
        );
    }

    public static Order createOrderWithStatus(String status) {
        return new Order(
            faker.internet().emailAddress(),
            new BigDecimal(faker.number().randomDouble(2, 10, 500)),
            status
        );
    }

    public static Payment createValidPayment(Long orderId) {
        return new Payment(
            orderId,
            new BigDecimal(faker.number().randomDouble(2, 10, 500)),
            faker.options().option("CREDIT_CARD", "DEBIT_CARD", "PAYPAL")
        );
    }

    public static Payment createPaymentWithAmount(Long orderId, BigDecimal amount) {
        return new Payment(
            orderId,
            amount,
            "CREDIT_CARD"
        );
    }

    public static Payment createPaymentWithMethod(Long orderId, String paymentMethod) {
        return new Payment(
            orderId,
            new BigDecimal(faker.number().randomDouble(2, 10, 500)),
            paymentMethod
        );
    }

    public static String generateEmail() {
        return faker.internet().emailAddress();
    }

    public static BigDecimal generateAmount() {
        return new BigDecimal(faker.number().randomDouble(2, 10, 500));
    }

    public static BigDecimal generateLargeAmount() {
        return new BigDecimal(faker.number().randomDouble(2, 1000, 5000));
    }
}
