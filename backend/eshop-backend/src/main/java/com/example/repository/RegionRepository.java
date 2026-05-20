package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Region;

public interface RegionRepository extends JpaRepository<Region, Integer> {

}
