package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderStatusById'");
    }

    @Override
    public List<OrderStatus> getAllOrderStatuses() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrderStatuses'");
    }

    @Override
    public void saveOrderStatus(OrderStatus orderStatus) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveOrderStatus'");
    }

    @Override
    public void deleteOrderStatusById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrderStatusById'");
    }

}
