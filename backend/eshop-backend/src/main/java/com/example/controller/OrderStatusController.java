package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.OrderStatus;
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
    public List<OrderStatus> listOrderStatuses() {
        return orderStatusService.getOrderStatuses();
    }

    // DETAIL
    @GetMapping("/{id}")
    public OrderStatus orderStatusDetail(@PathVariable Integer id) {
        return orderStatusService.getOrderStatusById(id);
    }

    // CREATE
    @PostMapping("/create")
    public OrderStatus createOrderStatus(@RequestBody OrderStatus orderStatus) {
        return orderStatusService.saveOrderStatus(orderStatus);
    }

    // EDIT 
    @PutMapping ("/{id}")
    public OrderStatus editOrderStatus(
            @PathVariable Integer id,
            @ModelAttribute OrderStatus orderStatus) {
        return  orderStatusService.editOrderStatus(id, orderStatus);
    }

    // DELETE
    @DeleteMapping ("/{id}")
    public void deleteOrderStatus(@PathVariable Integer id) {
        orderStatusService.deleteOrderStatusById(id);
    }
}
