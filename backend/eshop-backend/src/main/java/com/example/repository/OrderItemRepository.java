package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.OrderItem;

import jakarta.transaction.Transactional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE OrderItem oi
                SET oi.deleted = true,
                    oi.deletedAt = CURRENT_TIMESTAMP
                WHERE oi.orderItemId = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
