package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.model.Discount;

public interface DiscountRepository extends JpaRepository<Discount, Integer> {

}
