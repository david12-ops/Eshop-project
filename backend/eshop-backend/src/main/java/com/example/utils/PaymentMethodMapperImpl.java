package com.example.utils;

import org.springframework.stereotype.Component;

import com.example.dto.PaymentMethodDTO;
import com.example.exception.BadRequestException;
import com.example.model.PaymentMethod;
import com.example.utils.utils_interface.ObjectMapper;

import org.springframework.lang.NonNull;

@Component
public class PaymentMethodMapperImpl implements ObjectMapper<PaymentMethod, PaymentMethodDTO> {

    @Override
    @NonNull
    public PaymentMethod mapToEntity(PaymentMethodDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if ((objectDTO.getMethodName() == null || objectDTO.getMethodName().isBlank()) ||
                objectDTO.getMethodType() == null || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Payment method name, payment method type and deletion state are required");
        }

        PaymentMethod paymentMethod = new PaymentMethod();

        paymentMethod.setMethodName(objectDTO.getMethodName());
        paymentMethod.setMethodType(objectDTO.getMethodType());
        paymentMethod.setMethodDescription(objectDTO.getMethodDescription());
        paymentMethod.setDeleted(objectDTO.isDeleted());
        paymentMethod.setDeletedAt(objectDTO.getDeletedAt());
        paymentMethod.setCreatedAt(objectDTO.getCreatedAt());
        paymentMethod.setUpdatedAt(objectDTO.getUpdatedAt());
        paymentMethod.setCreatedBy(objectDTO.getCreatedBy());
        paymentMethod.setUpdatedBy(objectDTO.getUpdatedBy());

        return paymentMethod;
    }

    @Override
    public PaymentMethodDTO mapToDTO(PaymentMethod objectEntity) {
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO();

        paymentMethodDTO.setPaymentMethodId(objectEntity.getPaymentMethodId());
        paymentMethodDTO.setMethodName(objectEntity.getMethodName());
        paymentMethodDTO.setMethodType(objectEntity.getMethodType());
        paymentMethodDTO.setMethodDescription(objectEntity.getMethodDescription());
        paymentMethodDTO.setDeleted(objectEntity.isDeleted());
        paymentMethodDTO.setDeletedAt(objectEntity.getDeletedAt());
        paymentMethodDTO.setCreatedAt(objectEntity.getCreatedAt());
        paymentMethodDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        paymentMethodDTO.setCreatedBy(objectEntity.getCreatedBy());
        paymentMethodDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return paymentMethodDTO;
    }
}
