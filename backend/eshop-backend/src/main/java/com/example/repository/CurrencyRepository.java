package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Currency;

import jakarta.transaction.Transactional;

public interface CurrencyRepository extends JpaRepository<Currency, String> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE Currency cur
                SET cur.deleted = true,
                    cur.deletedAt = CURRENT_TIMESTAMP
                WHERE cur.currencyCode = :id
            """)
    void softDeleteById(@Param("id") String id);
}
