package com.example.service_interface;

import java.util.List;

import com.example.model.PaymentMethod;

public interface PaymentMethodService {

    PaymentMethod getPaymentMethodById(Integer id);

    List<PaymentMethod> getPaymentMethods();

    PaymentMethod savePaymentMethod(PaymentMethod paymentMethod);

    PaymentMethod editPaymentMethod(Integer id, PaymentMethod paymentMethod);

    void deletePaymentMethodById(Integer id);
}
