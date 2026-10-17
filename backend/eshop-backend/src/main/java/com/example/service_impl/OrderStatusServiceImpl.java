package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.OrderStatusDTO;
import com.example.exception.BadRequestException;
import com.example.model.MyUserDetail;
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
    public OrderStatusDTO getOrderStatusById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order status not found with id: " + id));
    }

    @Override
    public List<OrderStatusDTO> getOrderStatuses(MyUserDetail userDetail) {
        return orderStatusRepository.findAll();
    }

    @Override
    public OrderStatusDTO saveOrderStatus(OrderStatusDTO orderStatusDTO, MyUserDetail userDetail) {
        if (orderStatusDTO == null) {
            throw new BadRequestException("Order status must not be null");
        }

        return orderStatusRepository.save(orderStatus);
    }

    // Soft deletion
    @Override
    public void deleteOrderStatusById(Integer id, MyUserDetail userDetail) {
        OrderStatus existingOrderStatus = getOrderStatusById(id);

        if (existingOrderStatus.isDeleted())
            return;

        orderStatusRepository.softDeleteById(existingOrderStatus.getOrderStatusId());
    }

    @Override
    public OrderStatusDTO editOrderStatus(Integer id, OrderStatusDTO orderStatusDTO, MyUserDetail userDetail) {
        if (orderStatusDTO == null) {
            throw new BadRequestException("Order status to edit must not be null");
        }

        OrderStatus existingOrderStatus = getOrderStatusById(id);

        existingOrderStatus.setStatusName(orderStatus.getStatusName());
        existingOrderStatus.setStatusType(orderStatus.getStatusType());
        existingOrderStatus.setStatusDescription(orderStatus.getStatusDescription());

        return orderStatusRepository.save(existingOrderStatus);
    }
}
