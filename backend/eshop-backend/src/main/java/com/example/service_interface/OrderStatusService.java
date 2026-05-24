package com.example.service_interface;

import java.util.List;

import com.example.model.OrderStatus;

public interface OrderStatusService {

    OrderStatus getOrderStatusById(Integer id);

    List<OrderStatus> getAllOrderStatuses();

    void saveOrderStatus(OrderStatus orderStatus);

    void deleteOrderStatusById(Integer id);
}
