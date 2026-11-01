package com.example.utils;

import com.example.dto.OrderStatusDTO;
import com.example.exception.BadRequestException;
import com.example.model.OrderStatus;
import com.example.utils.utils_interface.ObjectMapper;

import org.springframework.lang.NonNull;

public class OrderStatusMapperImpl implements ObjectMapper<OrderStatus, OrderStatusDTO> {

    @Override
    @NonNull
    public OrderStatus mapToEntity(OrderStatusDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if ((objectDTO.getStatusName() == null || objectDTO.getStatusName().isBlank()) ||
                objectDTO.getStatusType() == null || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Order status name, oreder status type and deletion state are required");
        }

        OrderStatus orderStatus = new OrderStatus();

        orderStatus.setStatusName(objectDTO.getStatusName());
        orderStatus.setStatusType(objectDTO.getStatusType());
        orderStatus.setStatusDescription(objectDTO.getStatusDescription());
        orderStatus.setDeleted(objectDTO.isDeleted());
        orderStatus.setDeletedAt(objectDTO.getDeletedAt());
        orderStatus.setCreatedAt(objectDTO.getCreatedAt());
        orderStatus.setUpdatedAt(objectDTO.getUpdatedAt());
        orderStatus.setCreatedBy(objectDTO.getCreatedBy());
        orderStatus.setUpdatedBy(objectDTO.getUpdatedBy());

        return orderStatus;
    }

    @Override
    public OrderStatusDTO mapToDTO(OrderStatus objectEntity) {
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

        orderStatusDTO.setOrderStausId(objectEntity.getOrderStatusId());
        orderStatusDTO.setStatusName(objectEntity.getStatusName());
        orderStatusDTO.setStatusType(objectEntity.getStatusType());
        orderStatusDTO.setStatusDescription(objectEntity.getStatusDescription());
        orderStatusDTO.setDeleted(objectEntity.isDeleted());
        orderStatusDTO.setDeletedAt(objectEntity.getDeletedAt());
        orderStatusDTO.setCreatedAt(objectEntity.getCreatedAt());
        orderStatusDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        orderStatusDTO.setCreatedBy(objectEntity.getCreatedBy());
        orderStatusDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return orderStatusDTO;
    }
}
