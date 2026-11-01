package com.example.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.dto.OrderDTO;
import com.example.dto.OrderItemDTO;
import com.example.exception.BadRequestException;
import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class OrderItemMapperImpl implements ObjectMapper<OrderItem, OrderItemDTO> {
    private final ObjectMapper<Order, OrderDTO> orderMapper;

    public OrderItemMapperImpl(ObjectMapper<Order, OrderDTO> orderMapper) {
        this.orderMapper = orderMapper;
    }

    @Override
    @NonNull
    public OrderItem mapToEntity(OrderItemDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if (objectDTO.getProductId() == null || objectDTO.getQuantity() == null
                || objectDTO.getLineTotal() == null || objectDTO.getUnitPrice() == null
                || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Product id, quantity, line total, unit price and deletion state are required");
        }

        OrderItem orderItem = new OrderItem();

        orderItem.setProductId(objectDTO.getProductId());
        orderItem.setQuantity(objectDTO.getQuantity());
        orderItem.setLineTotal(objectDTO.getLineTotal());
        orderItem.setNotes(objectDTO.getNotes());
        orderItem.setUnitPrice(objectDTO.getUnitPrice());
        orderItem.setDeleted(objectDTO.isDeleted());
        orderItem.setDeletedAt(objectDTO.getDeletedAt());
        orderItem.setOrder(orderMapper.mapToEntity(objectDTO.getOrderDTO()));

        return orderItem;
    }

    @Override
    public OrderItemDTO mapToDTO(OrderItem objectEntity) {
        OrderItemDTO orderItemDTO = new OrderItemDTO();

        orderItemDTO.setOrderItemId(objectEntity.getOrderItemId());
        orderItemDTO.setProductId(objectEntity.getProductId());
        orderItemDTO.setQuantity(objectEntity.getQuantity());
        orderItemDTO.setLineTotal(objectEntity.getLineTotal());
        orderItemDTO.setNotes(objectEntity.getNotes());
        orderItemDTO.setUnitPrice(objectEntity.getUnitPrice());
        orderItemDTO.setDeleted(objectEntity.isDeleted());
        orderItemDTO.setDeletedAt(objectEntity.getDeletedAt());
        orderItemDTO.setOrderDTO(orderMapper.mapToDTO(objectEntity.getOrder()));

        return orderItemDTO;
    }
}
