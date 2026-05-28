package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Region;

import jakarta.transaction.Transactional;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE Region reg
                SET reg.deleted = true,
                    reg.deletedAt = CURRENT_TIMESTAMP
                WHERE reg.regionId = :id
            """)
    void softDeleteById(@Param("id") Integer id);
}
