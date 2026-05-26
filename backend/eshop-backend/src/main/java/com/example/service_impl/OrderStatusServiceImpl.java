package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.OrderStatus;
import com.example.repository.OrderStatusRepository;
import com.example.service_interface.OrderStatusService;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository) {
        this.orderStatusRepository = orderStatusRepository;
    }

    @Override
    public OrderStatus getOrderStatusById(Integer id) {
        return orderStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order status not found with id: " + id));
    }

    @Override
    public List<OrderStatus> getAllOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    @Override
    public void saveOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null)
            return;

        orderStatusRepository.save(orderStatus);
    }

    @Override
    public void deleteOrderStatusById(Integer id) {
        if (id == null)
            return;

        orderStatusRepository.findById(id).ifPresent(orderStatus -> orderStatusRepository.deleteById(id));
    }

    @Override
    public void editOrderStatus(Integer id, OrderStatus orderStatus) {
        if (id == null || orderStatus == null)
            return;

        orderStatusRepository.findById(id).ifPresent(existingOrderStatus -> {

            existingOrderStatus.setStatusName(orderStatus.getStatusName());
            existingOrderStatus.setStatusType(orderStatus.getStatusType());
            existingOrderStatus.setStatusDescription(orderStatus.getStatusDescription());

            orderStatusRepository.save(existingOrderStatus);
        });
    }
}
