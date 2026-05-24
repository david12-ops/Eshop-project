package com.example.service_impl;

import java.util.List;

import com.example.model.PaymentMethod;
import com.example.repository.PaymentMethodRepository;
import com.example.service_interface.PaymentMethodService;

public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public PaymentMethod getPaymentMethodById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentMethodById'");
    }

    @Override
    public List<PaymentMethod> getAllPaymentMethods() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllPaymentMethods'");
    }

    @Override
    public void savePaymentMethod(PaymentMethod paymentMethod) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'savePaymentMethod'");
    }

    @Override
    public void deletePaymentMethodById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deletePaymentMethodById'");
    }

}
