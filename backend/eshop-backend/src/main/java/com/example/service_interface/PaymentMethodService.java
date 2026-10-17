package com.example.service_interface;

import com.example.dto.PaymentMethodDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface PaymentMethodService {

    PaymentMethodDTO getPaymentMethodById(Integer id, MyUserDetail userDetail);

    List<PaymentMethodDTO> getPaymentMethods(MyUserDetail userDetail);

    PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO, MyUserDetail userDetail);

    PaymentMethodDTO editPaymentMethod(Integer id, PaymentMethodDTO paymentMethodDTO, MyUserDetail userDetail);

    void deletePaymentMethodById(Integer id, MyUserDetail userDetail);
}
