package com.example.service_interface;

import java.util.List;

import com.example.model.OrderItem;

public interface OrderItemService {

    OrderItem getOrderItemById(Integer id);

    List<OrderItem> getAllOrderItems();

    void saveOrderItem(OrderItem orderItem);

    void editOrderItem(Integer id, OrderItem orderItem);

    void deleteOrderItemById(Integer id);
}
