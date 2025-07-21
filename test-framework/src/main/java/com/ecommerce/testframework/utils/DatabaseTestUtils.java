package com.ecommerce.testframework.utils;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

public class DatabaseTestUtils {

    private final JdbcTemplate jdbcTemplate;

    public DatabaseTestUtils(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Transactional
    public void cleanupDatabase() {
        jdbcTemplate.execute("DELETE FROM payments");
        jdbcTemplate.execute("DELETE FROM orders");
        jdbcTemplate.execute("ALTER SEQUENCE orders_id_seq RESTART WITH 1");
        jdbcTemplate.execute("ALTER SEQUENCE payments_id_seq RESTART WITH 1");
    }

    public int countOrders() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM orders", Integer.class);
    }

    public int countPayments() {
        return jdbcTemplate.queryForObject("SELECT COUNT(*) FROM payments", Integer.class);
    }

    public List<Map<String, Object>> getAllOrders() {
        return jdbcTemplate.queryForList("SELECT * FROM orders ORDER BY id");
    }

    public List<Map<String, Object>> getAllPayments() {
        return jdbcTemplate.queryForList("SELECT * FROM payments ORDER BY id");
    }

    public Map<String, Object> getOrderById(Long id) {
        return jdbcTemplate.queryForMap("SELECT * FROM orders WHERE id = ?", id);
    }

    public Map<String, Object> getPaymentById(Long id) {
        return jdbcTemplate.queryForMap("SELECT * FROM payments WHERE id = ?", id);
    }

    public void insertTestOrder(String email, String amount, String status) {
        jdbcTemplate.update(
            "INSERT INTO orders (customer_email, amount, status, created_at, updated_at) VALUES (?, ?, ?, NOW(), NOW())",
            email, amount, status
        );
    }

    public void insertTestPayment(Long orderId, String amount, String paymentMethod, String status) {
        jdbcTemplate.update(
            "INSERT INTO payments (order_id, amount, payment_method, status, created_at, updated_at) VALUES (?, ?, ?, ?, NOW(), NOW())",
            orderId, amount, paymentMethod, status
        );
    }
}
