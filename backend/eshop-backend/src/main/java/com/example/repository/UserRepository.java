package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, Integer> {
    AppUser findByUsername(String userName);
}
