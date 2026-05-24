package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);

    @Query("""
                SELECT DISTINCT r
                FROM Role r
                LEFT JOIN FETCH r.users
                WHERE r.id = :id
            """)
    Optional<Role> findByIdWithUsers(@Param("id") Integer id);

    @Query("""
                SELECT DISTINCT r
                FROM Role r
                LEFT JOIN FETCH r.permissions
                WHERE r.id = :id
            """)
    Optional<Role> findByIdWithPermissions(@Param("id") Integer id);

    @Query("""
                SELECT DISTINCT r
                FROM Role r
                LEFT JOIN FETCH r.users
                LEFT JOIN FETCH r.permissions
                WHERE r.id = :id
            """)
    Optional<Role> findByIdWithUsersAndPermissions(@Param("id") Integer id);
}