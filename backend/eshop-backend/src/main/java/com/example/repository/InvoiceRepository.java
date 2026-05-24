package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
    @Query("""
                SELECT DISTINCT i
                FROM Invoice i
                LEFT JOIN FETCH i.items
                WHERE i.invoiceId = :id
            """)
    Optional<Invoice> findByIdWithItems(@Param("id") Integer id);
}
