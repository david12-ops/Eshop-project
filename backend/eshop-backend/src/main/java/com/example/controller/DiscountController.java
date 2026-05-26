package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Discount;
import com.example.service_interface.DiscountService;

@Controller
@RequestMapping("/discounts")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    // LIST
    @GetMapping
    public String listDiscounts(Model model) {

        model.addAttribute("discounts", discountService.getAllDiscounts());

        return "discounts/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String discountDetail(
            @PathVariable Integer id,
            Model model) {

        Discount discount = discountService.getDiscountById(id);

        model.addAttribute(
                "discount",
                discount);

        return "discounts/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "discount",
                new Discount());

        return "discounts/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createDiscount(
            @ModelAttribute Discount discount) {

        discountService.saveDiscount(discount);

        return "redirect:/discounts";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        Discount discount = discountService.getDiscountById(id);

        model.addAttribute(
                "discount",
                discount);

        return "discounts/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editDiscount(
            @PathVariable Integer id,
            @ModelAttribute Discount discount) {

        discountService.editDiscount(id, discount);

        return "redirect:/discounts";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteDiscount(
            @PathVariable Integer id) {

        discountService.deleteDiscountById(id);

        return "redirect:/discounts";
    }
}
