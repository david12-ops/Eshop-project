package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderItemDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.OrderItemService;

@RestController
@RequestMapping("/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    // LIST
    @GetMapping
    public List<OrderItemDTO> listOrderItems(@AuthenticationPrincipal MyUserDetail userDetail) {
        return orderItemService.getAllOrderItems(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public OrderItemDTO orderItemDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return orderItemService.getOrderItemById(id, userDetail);
    }

    // CREATE
    @PostMapping("/{orderId}")
    public OrderItemDTO createOrderItem(
            @PathVariable Integer orderId,
            @RequestBody OrderItemDTO orderItemDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return orderItemService.saveOrderItem(orderItemDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public OrderItemDTO editOrderItem(
            @PathVariable Integer id,
            @RequestBody OrderItemDTO orderItemDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return orderItemService.editOrderItem(id, orderItemDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteOrderItem(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        orderItemService.deleteOrderItemById(id, userDetail);
    }
}
