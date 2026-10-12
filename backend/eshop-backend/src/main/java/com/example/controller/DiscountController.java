package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Discount;
import com.example.service_interface.DiscountService;

@RestController 
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    // LIST
    @GetMapping
    public List<Discount> listDiscounts() {
        return discountService.getDiscounts();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Discount discountDetail(@PathVariable Integer id) {
        return discountService.getDiscountById(id);
    }

    // CREATE
    @PostMapping("/create")
    public Discount createDiscount(
            @RequestBody Discount discount) {
       return discountService.saveDiscount(discount);
    }

    // EDIT
    @PutMapping("/{id}")
    public Discount editDiscount(
            @PathVariable Integer id,
            @RequestBody  Discount discount) {
        return discountService.editDiscount(id, discount);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteDiscount(@PathVariable Integer id) {
        discountService.deleteDiscountById(id);
    }
}
