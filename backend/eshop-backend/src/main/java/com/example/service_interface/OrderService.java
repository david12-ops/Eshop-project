package com.example.service_interface;

import java.util.List;

import com.example.model.Order;

public interface OrderService {

    Order getOrderWithItems(Integer id);

    List<Order> getAllOrders();

    void saveOrder(Order order);

    void editOrder(Integer id, Order order);

    void deleteOrderById(Integer id);
}
