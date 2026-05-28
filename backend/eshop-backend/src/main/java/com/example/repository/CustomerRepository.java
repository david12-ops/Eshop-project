package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Customer;

import jakarta.transaction.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE Customer c
                SET c.deleted = true,
                    c.deletedAt = CURRENT_TIMESTAMP
                WHERE c.customerId = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
