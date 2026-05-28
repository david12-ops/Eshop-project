package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.model.PaymentMethod;
import com.example.model.enums.PaymentMethodType;
import com.example.service_interface.PaymentMethodService;

@Controller
@RequestMapping("/paymentMethods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    // LIST
    @GetMapping
    public String listPaymentMethods(Model model) {

        model.addAttribute("paymentMethods", paymentMethodService.getAllPaymentMethods());

        return "paymentMethods/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String paymentMethodDetail(
            @PathVariable Integer id,
            Model model) {

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);

        model.addAttribute(
                "paymentMethod",
                paymentMethod);

        return "paymentMethods/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "paymentMethod",
                new PaymentMethod());
        model.addAttribute("paymentMethodTypes", PaymentMethodType.values());

        return "paymentMethods/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createPaymentMethod(
            @ModelAttribute PaymentMethod paymentMethod) {

        paymentMethodService.savePaymentMethod(paymentMethod);

        return "redirect:/paymentMethods";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethodById(id);

        model.addAttribute(
                "paymentMethod",
                paymentMethod);
        model.addAttribute("paymentMethodTypes", PaymentMethodType.values());

        return "paymentMethods/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editPaymentMethod(
            @PathVariable Integer id,
            @ModelAttribute PaymentMethod paymentMethod) {

        paymentMethodService.editPaymentMethod(id, paymentMethod);

        return "redirect:/paymentMethods";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deletePaymentMethod(
            @PathVariable Integer id) {

        paymentMethodService.deletePaymentMethodById(id);

        return "redirect:/paymentMethods";
    }
}
