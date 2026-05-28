package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.OrderStatus;

import jakarta.transaction.Transactional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE OrderStatus o
                SET o.deleted = true,
                    o.deletedAt = CURRENT_TIMESTAMP
                WHERE o.id = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
