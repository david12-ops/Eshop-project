package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CustomerDTO;
import com.example.model.MyUserDetail;
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
    public List<CustomerDTO> listCustomers(@AuthenticationPrincipal MyUserDetail userDetail) {
        return customerService.getCustomers(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public CustomerDTO customerDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return customerService.getCustomerById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public CustomerDTO createCustomer(
            @RequestBody CustomerDTO customerDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return customerService.saveCustomer(customerDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public CustomerDTO editCustomer(
            @PathVariable Integer id,
            @RequestBody CustomerDTO customerDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return customerService.editCustomer(id, customerDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteCustomer(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        customerService.deleteCustomerById(id, userDetail);
    }
}
