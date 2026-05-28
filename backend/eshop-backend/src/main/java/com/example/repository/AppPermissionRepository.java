package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.AppPermission;
import com.example.model.keys.AppPermissionId;

import jakarta.transaction.Transactional;

public interface AppPermissionRepository extends JpaRepository<AppPermission, AppPermissionId> {
    @Modifying
    @Transactional
    @Query("""
                UPDATE AppPermission perm
                SET perm.deleted = true,
                    perm.deletedAt = CURRENT_TIMESTAMP
                WHERE perm.role.id = :#{#id.roleId}
                  AND perm.operationType = :#{#id.operationType}
                  AND perm.resourceType = :#{#id.resourceType}
            """)
    void softDeleteById(@Param("id") AppPermissionId id);
}
