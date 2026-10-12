package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Customer;
import com.example.service_interface.CustomerService;

@RestController 
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // LIST
    @GetMapping
    public List<Customer> listCustomers() {
        return customerService.getCustomers();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Customer customerDetail(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    // CREATE
    @PostMapping("/create")
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.saveCustomer(customer);
    }

    // EDIT
    @PutMapping ("/{id}")
    public Customer editCustomer(
            @PathVariable Integer id,
            @RequestBody Customer customer) {
        return customerService.editCustomer(id, customer);
    }

    // DELETE
    @DeleteMapping ("/{id}")
    public void deleteCustomer(@PathVariable Integer id) {
        customerService.deleteCustomerById(id);
    }
}
