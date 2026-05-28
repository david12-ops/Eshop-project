package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.PaymentMethod;

import jakarta.transaction.Transactional;

public interface PaymentMethodRepository extends JpaRepository<PaymentMethod, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE PaymentMethod p
                SET p.deleted = true,
                    p.deletedAt = CURRENT_TIMESTAMP
                WHERE p.id = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
