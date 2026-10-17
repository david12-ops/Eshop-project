package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // LIST
    @GetMapping
    public List<OrderDTO> listOrders(@AuthenticationPrincipal MyUserDetail userDetail) {
        return orderService.getOrders(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public OrderDTO orderDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return orderService.getOrderWithItems(id, userDetail);
    }
}
