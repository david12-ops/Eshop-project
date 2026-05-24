package com.example.service_interface;

import java.util.List;

import com.example.model.Discount;

public interface DiscountService {

    Discount getDiscountById(Integer id);

    List<Discount> getAllDiscounts();

    void saveDiscount(Discount discount);

    void deleteDiscountById(Integer id);
}
