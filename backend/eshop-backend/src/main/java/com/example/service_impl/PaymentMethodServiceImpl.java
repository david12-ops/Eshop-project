package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.PaymentMethod;
import com.example.repository.PaymentMethodRepository;
import com.example.service_interface.PaymentMethodService;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public PaymentMethod getPaymentMethodById(Integer id) {
        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + id));
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public void savePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null)
            return;

        paymentMethodRepository.save(paymentMethod);
    }

    @Override
    public void deletePaymentMethodById(Integer id) {
        if (id == null)
            return;

        // zatím ne
        paymentMethodRepository.findById(id).ifPresent(paymentMethod -> {
            if (paymentMethod.getDeleted())
                return; // tady jen soft kvůli klíčům

            paymentMethodRepository.softDeleteById(id);
        });
    }

    @Override
    public void editPaymentMethod(Integer id, PaymentMethod paymentMethod) {
        if (id == null || paymentMethod == null)
            return;

        paymentMethodRepository.findById(id).ifPresent(existingPaymentMethod -> {

            existingPaymentMethod.setMethodName(paymentMethod.getMethodName());
            existingPaymentMethod.setMethodType(paymentMethod.getMethodType());
            existingPaymentMethod.setMethodDescription(paymentMethod.getMethodDescription());

            paymentMethodRepository.save(existingPaymentMethod);
        });
    }
}
