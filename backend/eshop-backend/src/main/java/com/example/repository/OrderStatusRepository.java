package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.OrderStatus;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

}
