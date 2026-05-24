package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query("""
                SELECT DISTINCT c
                FROM Category c
                LEFT JOIN FETCH c.items
                WHERE c.id = :id
            """)
    Optional<Category> findByIdWithItems(@Param("id") Integer id);
}
