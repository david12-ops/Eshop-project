package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.PaymentMethodDTO;
import com.example.exception.BadRequestException;
import com.example.model.MyUserDetail;
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
    public PaymentMethodDTO getPaymentMethodById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + id));
    }

    @Override
    public List<PaymentMethodDTO> getPaymentMethods(MyUserDetail userDetail) {
        return paymentMethodRepository.findAll();
    }

    @Override
    public PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO, MyUserDetail userDetail) {
        if (paymentMethodDTO == null) {
            throw new BadRequestException("Payment method must not be null");
        }

        return paymentMethodRepository.save(paymentMethod);
    }

    // Soft deletion
    @Override
    public void deletePaymentMethodById(Integer id, MyUserDetail userDetail) {
        PaymentMethod existingPaymentMethod = getPaymentMethodById(id);

        if (existingPaymentMethod.iusDeleted())
            return;

        paymentMethodRepository.softDeleteById(existingPaymentMethod.getPaymentMethodId());
    }

    @Override
    public PaymentMethodDTO editPaymentMethod(Integer id, PaymentMethodDTO paymentMethodDTO, MyUserDetail userDetail) {
        if (paymentMethodDTO == null) {
            throw new BadRequestException("Payment method to edit must not be null");
        }

        PaymentMethod existingPaymentMethod = getPaymentMethodById(id);

        existingPaymentMethod.setMethodName(paymentMethod.getMethodName());
        existingPaymentMethod.setMethodType(paymentMethod.getMethodType());
        existingPaymentMethod.setMethodDescription(paymentMethod.getMethodDescription());

        return paymentMethodRepository.save(existingPaymentMethod);
    }
}
