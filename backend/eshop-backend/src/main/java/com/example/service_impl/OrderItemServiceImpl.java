package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
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
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        if (orderItem == null)
            return;

        orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteOrderItemById(Integer id) {
        if (id == null)
            return;

        orderItemRepository.findById(id).ifPresent(orderItem -> orderItemRepository.deleteById(id));
    }

    @Override
    public void editOrderItem(Integer id, OrderItem orderItem) {
        if (id == null || orderItem == null)
            return;

        orderItemRepository.findById(id).ifPresent(existingOrderItem -> {

            existingOrderItem.setLineTotal(orderItem.getLineTotal());
            existingOrderItem.setQuantity(orderItem.getQuantity());
            existingOrderItem.setNotes(orderItem.getNotes());
            existingOrderItem.setUnitPrice(orderItem.getUnitPrice());

            orderItemRepository.save(existingOrderItem);
        });
    }
}
