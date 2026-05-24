package com.example.service_interface;

import java.util.List;

import com.example.model.PaymentMethod;

public interface PaymentMethodService {

    PaymentMethod getPaymentMethodById(Integer id);

    List<PaymentMethod> getAllPaymentMethods();

    void savePaymentMethod(PaymentMethod paymentMethod);

    void deletePaymentMethodById(Integer id);
}
