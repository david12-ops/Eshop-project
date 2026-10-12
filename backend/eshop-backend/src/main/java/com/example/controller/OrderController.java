package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Order;
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
    public List<Order> listOrders() {
        return orderService.getOrders();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Order orderDetail(@PathVariable Integer id) {
        return orderService.getOrderWithItems(id);
    }

    // CREATE
    @PostMapping("/create")
    public Order createOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    // EDIT 
    @PutMapping ("/{id}")
    public Order editOrder(
            @PathVariable Integer id,
            @RequestBody Order order) {
        return orderService.editOrder(id, order);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderService.deleteOrderById(id);
    }
}
