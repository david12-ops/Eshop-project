package com.example.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
    @Query("""
                SELECT u
                FROM AppUser u
                JOIN FETCH u.role r
                LEFT JOIN FETCH r.permissions
                WHERE u.username = :username
            """)
    Optional<AppUser> findByUsername(@Param("username") String userName);
}
