package com.EcommerceWeb.Foodzy.Repository;

import com.EcommerceWeb.Foodzy.Entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderOrderId(Long orderId);
}
