package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
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
    public OrderItem getOrderItemById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        if (orderItem == null) {
            throw new BadRequestException("Order item must not be null");
        }

        return orderItemRepository.save(orderItem);
    }

    // Soft deletion
    @Override
    public void deleteOrderItemById(Integer id) {
        OrderItem existingOrderItem = getOrderItemById(id);

        if (existingOrderItem.getDeleted())
            return;

        orderItemRepository.softDeleteById(existingOrderItem.getOrderItemId());
    }

    @Override
    public OrderItem editOrderItem(Integer id, OrderItem orderItem) {
        if (orderItem == null) {
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
