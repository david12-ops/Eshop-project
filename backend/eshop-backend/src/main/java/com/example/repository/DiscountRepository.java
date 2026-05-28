package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Discount;

import jakarta.transaction.Transactional;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE Discount d
                SET d.deleted = true,
                    d.deletedAt = CURRENT_TIMESTAMP
                WHERE d.discountId = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
