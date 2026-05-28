package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Address;

import jakarta.transaction.Transactional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE Address a
                SET a.deleted = true,
                    a.deletedAt = CURRENT_TIMESTAMP
                WHERE a.id = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
