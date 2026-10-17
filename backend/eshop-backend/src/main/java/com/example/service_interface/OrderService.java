package com.example.service_interface;

import com.example.dto.OrderDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface OrderService {

    OrderDTO getOrderWithItems(Integer id, MyUserDetail userDetail);

    List<OrderDTO> getOrders(MyUserDetail userDetail);

    OrderDTO saveOrder(OrderDTO orderDTO, MyUserDetail userDetail);

    OrderDTO editOrder(Integer id, OrderDTO orderDTO, MyUserDetail userDetail);

    void deleteOrderById(Integer id, MyUserDetail userDetail);
}
