package com.example.service_interface;

import java.util.List;

import com.example.model.Customer;

public interface CustomerService {

    Customer getCustomerById(Integer id);

    List<Customer> getAllCustomers();

    void saveCustomer(Customer customer);

    void editCustomer(Integer id, Customer customer);

    void deleteCustomerById(Integer id);
}
