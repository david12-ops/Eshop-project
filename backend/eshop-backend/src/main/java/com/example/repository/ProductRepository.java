package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Product;

import jakarta.transaction.Transactional;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE Product p
                SET p.deleted = true,
                    p.deletedAt = CURRENT_TIMESTAMP,
                    p.active = false
                WHERE p.id = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
