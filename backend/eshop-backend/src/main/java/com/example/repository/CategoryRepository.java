package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Category;

import jakarta.transaction.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE Category cg
                SET cg.deleted = true,
                    cg.deletedAt = CURRENT_TIMESTAMP,
                    cg.active = false
                WHERE cg.id = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
