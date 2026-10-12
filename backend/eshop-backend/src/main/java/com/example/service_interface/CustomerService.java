package com.example.service_interface;

import java.util.List;

import com.example.model.Customer;

public interface CustomerService {

    Customer getCustomerById(Integer id);

    List<Customer> getCustomers();

    Customer saveCustomer(Customer customer);

    Customer editCustomer(Integer id, Customer customer);

    void deleteCustomerById(Integer id);
}
