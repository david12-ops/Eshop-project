package com.example.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.dto.DiscountDTO;
import com.example.exception.BadRequestException;
import com.example.model.Discount;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class DiscountMapperImpl implements ObjectMapper<Discount, DiscountDTO> {

    @Override
    @NonNull
    public Discount mapToEntity(DiscountDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if ((objectDTO.getDiscountName() == null || objectDTO.getDiscountName().isBlank()) ||
                (objectDTO.getDiscountCode() == null || objectDTO.getDiscountCode().isBlank()) ||
                objectDTO.getDiscountType() == null || objectDTO.getDiscountValue() == null
                || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Discount name, discount code, discount type, discount value and deletion state are required");
        }

        Discount discount = new Discount();

        discount.setDiscountName(objectDTO.getDiscountName());
        discount.setDiscountCode(objectDTO.getDiscountCode());
        discount.setDiscountType(objectDTO.getDiscountType());
        discount.setDiscountValue(objectDTO.getDiscountValue());
        discount.setDiscountDescription(objectDTO.getDiscountDescription());
        discount.setValidFrom(objectDTO.getValidFrom());
        discount.setValidTo(objectDTO.getValidTo());
        discount.setDeleted(objectDTO.isDeleted());
        discount.setDeletedAt(objectDTO.getDeletedAt());
        discount.setCreatedAt(objectDTO.getCreatedAt());
        discount.setUpdatedAt(objectDTO.getUpdatedAt());
        discount.setCreatedBy(objectDTO.getCreatedBy());
        discount.setUpdatedBy(objectDTO.getUpdatedBy());

        return discount;
    }

    @Override
    public DiscountDTO mapToDTO(Discount objectEntity) {
        DiscountDTO discountDTO = new DiscountDTO();

        discountDTO.setDiscountId(objectEntity.getDiscountId());
        discountDTO.setDiscountName(objectEntity.getDiscountName());
        discountDTO.setDiscountCode(objectEntity.getDiscountCode());
        discountDTO.setDiscountType(objectEntity.getDiscountType());
        discountDTO.setDiscountValue(objectEntity.getDiscountValue());
        discountDTO.setDiscountDescription(objectEntity.getDiscountDescription());
        discountDTO.setValidFrom(objectEntity.getValidFrom());
        discountDTO.setValidTo(objectEntity.getValidTo());
        discountDTO.setDeleted(objectEntity.isDeleted());
        discountDTO.setDeletedAt(objectEntity.getDeletedAt());
        discountDTO.setCreatedAt(objectEntity.getCreatedAt());
        discountDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        discountDTO.setCreatedBy(objectEntity.getCreatedBy());
        discountDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return discountDTO;
    }
}
