package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Order;
import com.example.service_interface.OrderService;

@Controller
@RequestMapping("/orders")
public class OrdersController {
    private final OrderService orderService;

    public OrdersController(OrderService orderService) {
        this.orderService = orderService;
    }

    // LIST
    @GetMapping
    public String listOrders(Model model) {

        model.addAttribute("orders", orderService.getAllOrders());

        return "orders/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String orderDetail(
            @PathVariable Integer id,
            Model model) {

        Order order = orderService.getOrderWithItems(id);

        model.addAttribute(
                "order",
                order);

        return "orders/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "order",
                new Order());

        return "orders/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createOrder(
            @ModelAttribute Order order) {

        orderService.saveOrder(order);

        return "redirect:/orders";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        Order order = orderService.getOrderWithItems(id);

        model.addAttribute(
                "order",
                order);

        return "orders/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editOrder(
            @PathVariable Integer id,
            @ModelAttribute Order order) {

        orderService.editOrder(id, order);

        return "redirect:/orders";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteOrder(
            @PathVariable Integer id) {

        orderService.deleteOrderById(id);

        return "redirect:/orders";
    }
}
