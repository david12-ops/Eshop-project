package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOrderItemById'");
    }

    @Override
    public List<OrderItem> getAllOrderItems() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllOrderItems'");
    }

    @Override
    public void saveOrderItem(OrderItem orderItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveOrderItem'");
    }

    @Override
    public void deleteOrderItemById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteOrderItemById'");
    }

}
