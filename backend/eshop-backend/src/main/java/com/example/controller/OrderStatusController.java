package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.OrderStatus;
import com.example.service_interface.OrderStatusService;

@Controller
@RequestMapping("/orderStatuses")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    // LIST
    @GetMapping
    public String listOrderStatuses(Model model) {

        model.addAttribute("orderStatuses", orderStatusService.getAllOrderStatuses());

        return "orderStatuses/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String orderStatusDetail(
            @PathVariable Integer id,
            Model model) {

        OrderStatus orderStatus = orderStatusService.getOrderStatusById(id);

        model.addAttribute(
                "orderStatus",
                orderStatus);

        return "orderStatuses/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "orderStatus",
                new OrderStatus());

        return "orderStatuses/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createOrderStatus(
            @ModelAttribute OrderStatus orderStatus) {

        orderStatusService.saveOrderStatus(orderStatus);

        return "redirect:/orderStatuses";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        OrderStatus orderStatus = orderStatusService.getOrderStatusById(id);

        model.addAttribute(
                "orderStatus",
                orderStatus);

        return "orderStatuses/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editOrderStatus(
            @PathVariable Integer id,
            @ModelAttribute OrderStatus orderStatus) {

        orderStatusService.editOrderStatus(id, orderStatus);

        return "redirect:/orderStatuses";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteOrderStatus(
            @PathVariable Integer id) {

        orderStatusService.deleteOrderStatusById(id);

        return "redirect:/orderStatuses";
    }
}
