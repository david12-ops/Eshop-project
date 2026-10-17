package com.example.service_interface;

import com.example.dto.OrderItemDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface OrderItemService {

    OrderItemDTO getOrderItemById(Integer id, MyUserDetail userDetail);

    List<OrderItemDTO> getAllOrderItems(MyUserDetail userDetail);

    OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO, MyUserDetail userDetail);

    OrderItemDTO editOrderItem(Integer id, OrderItemDTO orderItemDTO, MyUserDetail userDetail);

    void deleteOrderItemById(Integer id, MyUserDetail userDetail);
}
