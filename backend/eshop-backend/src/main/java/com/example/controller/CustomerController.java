package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Customer;
import com.example.service_interface.AddressService;
import com.example.service_interface.CustomerService;
import com.example.service_interface.UserService;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;
    private final AddressService addressService;
    private final UserService userService;

    public CustomerController(CustomerService customerService, AddressService addressService, UserService userService) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.userService = userService;
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
        model.addAttribute("addresses", addressService.getAllAddresses());
        model.addAttribute("users", userService.getAllUsers());

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

        System.out.println("Customer  " + customer.toString());

        model.addAttribute(
                "customer",
                customer);
        model.addAttribute("addresses", addressService.getAllAddresses());
        model.addAttribute("users", userService.getAllUsers());

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
