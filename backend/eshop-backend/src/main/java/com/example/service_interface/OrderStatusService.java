package com.example.service_interface;

import com.example.dto.OrderStatusDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface OrderStatusService {

    OrderStatusDTO getOrderStatusById(Integer id, MyUserDetail userDetail);

    List<OrderStatusDTO> getOrderStatuses(MyUserDetail userDetail);

    OrderStatusDTO saveOrderStatus(OrderStatusDTO orderStatusDTO, MyUserDetail userDetail);

    OrderStatusDTO editOrderStatus(Integer id, OrderStatusDTO orderStatusDTO, MyUserDetail userDetail);

    void deleteOrderStatusById(Integer id, MyUserDetail userDetail);
}
