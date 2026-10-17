package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.OrderStatusDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.OrderStatusService;

@RestController
@RequestMapping("/orderStatuses")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    // LIST
    @GetMapping
    public List<OrderStatusDTO> listOrderStatuses(@AuthenticationPrincipal MyUserDetail userDetail) {
        return orderStatusService.getOrderStatuses(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public OrderStatusDTO orderStatusDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return orderStatusService.getOrderStatusById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public OrderStatusDTO createOrderStatus(
            @RequestBody OrderStatusDTO orderStatusDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return orderStatusService.saveOrderStatus(orderStatusDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public OrderStatusDTO editOrderStatus(
            @PathVariable Integer id,
            @ModelAttribute OrderStatusDTO orderStatusDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return orderStatusService.editOrderStatus(id, orderStatusDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteOrderStatus(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        orderStatusService.deleteOrderStatusById(id, userDetail);
    }
}
