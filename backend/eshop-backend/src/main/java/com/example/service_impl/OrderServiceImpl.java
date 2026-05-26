package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Order;
import com.example.repository.OrderRepository;
import com.example.service_interface.OrderService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void saveOrder(Order order) {
        if (order == null)
            return;

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order getOrderWithItems(Integer id) {
        return orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public void deleteOrderById(Integer id) {
        if (id == null)
            return;

        orderRepository.findById(id).ifPresent(order -> orderRepository.deleteById(id));
    }

    @Override
    public void editOrder(Integer id, Order order) {
        if (id == null || order == null)
            return;

        orderRepository.findById(id).ifPresent(existingOrder -> {

            existingOrder.setOrderDate(order.getOrderDate());
            existingOrder.setSubtotal(order.getSubtotal());
            existingOrder.setTaxAmount(order.getTaxAmount());
            existingOrder.setTotalAmount(order.getTotalAmount());
            existingOrder.setNotes(order.getNotes());

            orderRepository.save(existingOrder);
        });
    }
}
