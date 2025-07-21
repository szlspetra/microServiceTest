package com.ecommerce.order.repository;

import com.ecommerce.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerEmail(String customerEmail);

    List<Order> findByStatus(String status);

    @Query("SELECT o FROM Order o WHERE o.customerEmail = :email AND o.status = :status")
    List<Order> findByCustomerEmailAndStatus(@Param("email") String email, @Param("status") String status);

    Optional<Order> findByIdAndCustomerEmail(Long id, String customerEmail);
}
