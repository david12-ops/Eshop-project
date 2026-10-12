package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
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
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order status not found with id: " + id));
    }

    @Override
    public List<OrderStatus> getOrderStatuses() {
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatus saveOrderStatus(OrderStatus orderStatus) {
        if (orderStatus == null) {
            throw new BadRequestException("Order status must not be null");
        }

        return orderStatusRepository.save(orderStatus);
    }

    // Soft deletion
    @Override
    public void deleteOrderStatusById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        orderStatusRepository.findById(id).ifPresent(orderStatus -> {
            if (orderStatus.getDeleted())
                return;

            orderStatusRepository.softDeleteById(id);
        });
    }

    @Override
    public OrderStatus editOrderStatus(Integer id, OrderStatus orderStatus) {
        if (id == null || orderStatus == null) {
            throw new BadRequestException("ID or order status to edit must not be null");
        }

        return orderStatusRepository.findById(id).map(existingOrderStatus -> {
            existingOrderStatus.setStatusName(orderStatus.getStatusName());
            existingOrderStatus.setStatusType(orderStatus.getStatusType());
            existingOrderStatus.setStatusDescription(orderStatus.getStatusDescription());

            return orderStatusRepository.save(existingOrderStatus);
        }).orElse(null);
    }
}
