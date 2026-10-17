package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.DiscountDTO;
import com.example.model.MyUserDetail;
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
    public List<DiscountDTO> listDiscounts(@AuthenticationPrincipal MyUserDetail userDetail) {
        return discountService.getDiscounts(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public DiscountDTO discountDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return discountService.getDiscountById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public DiscountDTO createDiscount(
            @RequestBody DiscountDTO discountDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return discountService.saveDiscount(discountDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public DiscountDTO editDiscount(
            @PathVariable Integer id,
            @RequestBody DiscountDTO discountDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return discountService.editDiscount(id, discountDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteDiscount(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        discountService.deleteDiscountById(id, userDetail);
    }
}
