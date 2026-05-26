package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Customer;
import com.example.service_interface.CustomerService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // LIST
    @GetMapping
    public String listCustomers(Model model) {

        model.addAttribute("customers", customerService.getAllCustomers());

        return "customers/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String customerDetail(
            @PathVariable Integer id,
            Model model) {

        Customer customer = customerService.getCustomerById(id);

        model.addAttribute(
                "customer",
                customer);

        return "customers/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "customer",
                new Customer());

        return "customers/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createCustomer(
            @ModelAttribute Customer customer) {

        customerService.saveCustomer(customer);

        return "redirect:/customers";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        Customer customer = customerService.getCustomerById(id);

        model.addAttribute(
                "customer",
                customer);

        return "customers/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editCustomer(
            @PathVariable Integer id,
            @ModelAttribute Customer customer) {

        customerService.editCustomer(id, customer);

        return "redirect:/customers";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteCustomer(
            @PathVariable Integer id) {

        customerService.deleteCustomerById(id);

        return "redirect:/customers";
    }
}
