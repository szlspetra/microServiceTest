package com.ecommerce.payment.service;

import com.ecommerce.payment.model.Payment;
import com.ecommerce.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment createPayment(Long orderId, BigDecimal amount, String paymentMethod) {
        validatePaymentData(orderId, amount, paymentMethod);

        Payment payment = new Payment(orderId, amount, paymentMethod);
        return paymentRepository.save(payment);
    }

    public Payment processPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        // Simulate payment processing
        payment.setStatus("PROCESSING");
        payment.setTransactionId(UUID.randomUUID().toString());
        payment.setUpdatedAt(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        // Simulate async processing result
        boolean success = simulatePaymentGateway(payment.getAmount());

        if (success) {
            savedPayment.setStatus("COMPLETED");
        } else {
            savedPayment.setStatus("FAILED");
        }

        savedPayment.setUpdatedAt(LocalDateTime.now());
        return paymentRepository.save(savedPayment);
    }

    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public List<Payment> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public Payment updatePaymentStatus(Long paymentId, String newStatus) {
        Payment payment = paymentRepository.findById(paymentId)
            .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentId));

        payment.setStatus(newStatus);
        payment.setUpdatedAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    private void validatePaymentData(Long orderId, BigDecimal amount, String paymentMethod) {
        if (orderId == null || orderId <= 0) {
            throw new IllegalArgumentException("Order ID must be positive");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be null or empty");
        }
    }

    private boolean simulatePaymentGateway(BigDecimal amount) {
        // Simulate payment gateway processing
        // In real implementation, this would call external payment service
        return amount.compareTo(new BigDecimal("1000")) <= 0; // Fail if amount > 1000
    }
}
