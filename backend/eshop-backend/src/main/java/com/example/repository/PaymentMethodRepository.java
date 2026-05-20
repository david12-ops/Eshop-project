package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.PaymentMethod;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {

}
