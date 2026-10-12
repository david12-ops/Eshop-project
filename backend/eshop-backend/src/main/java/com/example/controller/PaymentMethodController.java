package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import com.example.model.PaymentMethod;
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
    public List<PaymentMethod> listPaymentMethods() {
        return paymentMethodService.getPaymentMethods();
    }

    // DETAIL
    @GetMapping("/{id}")
    public PaymentMethod paymentMethodDetail(@PathVariable Integer id) {
        return paymentMethodService.getPaymentMethodById(id);
    }

    // CREATE 
    @PostMapping("/create")
    public PaymentMethod createPaymentMethod(@RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.savePaymentMethod(paymentMethod);
    }

    // EDIT 
    @PutMapping ("/{id}")
    public PaymentMethod editPaymentMethod(
            @PathVariable Integer id,
            @RequestBody PaymentMethod paymentMethod) {
        return paymentMethodService.editPaymentMethod(id, paymentMethod);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deletePaymentMethod(@PathVariable Integer id) {
        paymentMethodService.deletePaymentMethodById(id);
    }
}
