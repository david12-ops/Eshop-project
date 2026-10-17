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

import com.example.dto.PaymentMethodDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.PaymentMethodService;

@RestController
@RequestMapping("/paymentMethods")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    // LIST
    @GetMapping
    public List<PaymentMethodDTO> listPaymentMethods(@AuthenticationPrincipal MyUserDetail userDetail) {
        return paymentMethodService.getPaymentMethods(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public PaymentMethodDTO paymentMethodDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return paymentMethodService.getPaymentMethodById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public PaymentMethodDTO createPaymentMethod(
            @RequestBody PaymentMethodDTO paymentMethodDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return paymentMethodService.savePaymentMethod(paymentMethodDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public PaymentMethodDTO editPaymentMethod(
            @PathVariable Integer id,
            @RequestBody PaymentMethodDTO paymentMethodDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return paymentMethodService.editPaymentMethod(id, paymentMethodDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletePaymentMethod(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        paymentMethodService.deletePaymentMethodById(id, userDetail);
    }
}
