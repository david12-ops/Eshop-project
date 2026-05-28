package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Order;
import com.example.service_interface.AddressService;
import com.example.service_interface.CurrencyService;
import com.example.service_interface.CustomerService;
import com.example.service_interface.OrderService;
import com.example.service_interface.OrderStatusService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final OrderStatusService orderStatusService;
    private final CustomerService customerService;
    private final CurrencyService currencyService;
    private final AddressService addressService;

    public OrderController(OrderService orderService, OrderStatusService orderStatusService,
            CustomerService customerService,
            CurrencyService currencyService, AddressService addressService) {
        this.orderService = orderService;
        this.orderStatusService = orderStatusService;
        this.customerService = customerService;
        this.currencyService = currencyService;
        this.addressService = addressService;
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

        model.addAttribute("statuses", orderStatusService.getAllOrderStatuses());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("addresses", addressService.getAllAddresses());

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

        model.addAttribute("statuses", orderStatusService.getAllOrderStatuses());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("currencies", currencyService.getAllCurrencies());
        model.addAttribute("addresses", addressService.getAllAddresses());

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
