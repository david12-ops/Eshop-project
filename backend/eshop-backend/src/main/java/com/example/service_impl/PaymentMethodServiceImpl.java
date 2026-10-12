package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
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
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + id));
    }

    @Override
    public List<PaymentMethod> getPaymentMethods() {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethod savePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new BadRequestException("Payment method must not be null");
        }

        return paymentMethodRepository.save(paymentMethod);
    }

    // Soft deletion
    @Override
    public void deletePaymentMethodById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        paymentMethodRepository.findById(id).ifPresent(paymentMethod -> {
            if (paymentMethod.getDeleted())
                return;

            paymentMethodRepository.softDeleteById(id);
        });
    }

    @Override
    public PaymentMethod editPaymentMethod(Integer id, PaymentMethod paymentMethod) {
        if (id == null || paymentMethod == null) {
            throw new BadRequestException("ID or payment method to edit must not be null");
        }

        return paymentMethodRepository.findById(id).map(existingPaymentMethod -> {
            existingPaymentMethod.setMethodName(paymentMethod.getMethodName());
            existingPaymentMethod.setMethodType(paymentMethod.getMethodType());
            existingPaymentMethod.setMethodDescription(paymentMethod.getMethodDescription());

            return paymentMethodRepository.save(existingPaymentMethod);
        }).orElse(null);
    }
}
