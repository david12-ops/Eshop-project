package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.OrderItem;
import com.example.service_interface.OrderItemService;

@Controller
@RequestMapping("/order-items")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String orderItemDetail(
            @PathVariable Integer id,
            Model model) {

        OrderItem orderItem = orderItemService.getOrderItemById(id);

        model.addAttribute(
                "orderItem",
                orderItem);

        return "orders/order-items/detail";
    }

    // Create item form in order detail page
    @GetMapping("/create/{orderId}")
    public String createForm(
            @PathVariable Integer orderId,
            Model model) {

        OrderItem orderItem = new OrderItem();

        model.addAttribute(
                "orderItem",
                orderItem);

        return "orders/order-items/create";
    }

    // Create item form in order detail page
    @PostMapping("/create/{orderId}")
    public String createForm(
            @PathVariable Integer orderId,
            @ModelAttribute OrderItem orderItem) {

        orderItemService.saveOrderItem(orderItem);

        return "redirect:/order-items";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        OrderItem orderItem = orderItemService.getOrderItemById(id);

        model.addAttribute(
                "orderItem",
                orderItem);

        return "orders/order-items/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editOrderItem(
            @PathVariable Integer id,
            @ModelAttribute OrderItem orderItem) {

        orderItemService.editOrderItem(id, orderItem);

        return "redirect:/order-items";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteOrderItem(
            @PathVariable Integer id) {

        orderItemService.deleteOrderItemById(id);

        return "redirect:/order-items";
    }
}
