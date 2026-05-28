package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Order;

import jakarta.transaction.Transactional;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("""
                SELECT DISTINCT o
                FROM Order o
                LEFT JOIN FETCH o.orderItems
                WHERE o.orderId = :id
            """)
    Optional<Order> findByIdWithItems(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query("""
                UPDATE Order o
                SET o.deleted = true,
                    o.deletedAt = CURRENT_TIMESTAMP
                WHERE o.orderId = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
