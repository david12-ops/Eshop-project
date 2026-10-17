package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.OrderItemDTO;
import com.example.exception.BadRequestException;
import com.example.model.MyUserDetail;
import com.example.model.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.service_interface.OrderItemService;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository) {
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public OrderItemDTO getOrderItemById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems(MyUserDetail userDetail) {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO, MyUserDetail userDetail) {
        if (orderItemDTO == null) {
            throw new BadRequestException("Order item must not be null");
        }

        return orderItemRepository.save(orderItem);
    }

    // Soft deletion
    @Override
    public void deleteOrderItemById(Integer id, MyUserDetail userDetail) {
        OrderItem existingOrderItem = getOrderItemById(id);

        if (existingOrderItem.isDeleted())
            return;

        orderItemRepository.softDeleteById(existingOrderItem.getOrderItemId());
    }

    @Override
    public OrderItemDTO editOrderItem(Integer id, OrderItemDTO orderItemDTO, MyUserDetail userDetail) {
        if (orderItemDTO == null) {
            throw new BadRequestException("Order item to edit must not be null");
        }

        OrderItem existingOrderItem = getOrderItemById(id);

        existingOrderItem.setLineTotal(orderItem.getLineTotal());
        existingOrderItem.setQuantity(orderItem.getQuantity());
        existingOrderItem.setNotes(orderItem.getNotes());
        existingOrderItem.setUnitPrice(orderItem.getUnitPrice());

        return orderItemRepository.save(existingOrderItem);
    }
}
