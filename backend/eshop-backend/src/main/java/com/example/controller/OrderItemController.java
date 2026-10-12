package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.OrderItem;
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
    public List<OrderItem> listOrderItems(){
        return orderItemService.getAllOrderItems();
    }

    // DETAIL
    @GetMapping("/{id}")
    public OrderItem orderItemDetail(@PathVariable Integer id) {
        return orderItemService.getOrderItemById(id);
    }

    // CREATE
    @PostMapping("/{orderId}")
    public OrderItem createOrderItem(
            @PathVariable Integer orderId,
            @RequestBody OrderItem orderItem) {
        return orderItemService.saveOrderItem(orderItem);
    }

    // EDIT 
    @PutMapping("/{id}")
    public OrderItem editOrderItem(
            @PathVariable Integer id,
            @RequestBody OrderItem orderItem) {
        return orderItemService.editOrderItem(id, orderItem);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteOrderItem(@PathVariable Integer id) {
        orderItemService.deleteOrderItemById(id);
    }
}
