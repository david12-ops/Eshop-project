package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.service_interface.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customer getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void saveCustomer(Customer customer) {
        if (customer == null)
            return;

        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomerById(Integer id) {
        if (id == null)
            return;

        customerRepository.findById(id).ifPresent(customer -> customerRepository.deleteById(id));
    }

    @Override
    public void editCustomer(Integer id, Customer customer) {
        if (id == null || customer == null)
            return;

        customerRepository.findById(id).ifPresent(existingCustomer -> {

            existingCustomer.setBranchName(customer.getBranchName());
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setBirthDate(customer.getBirthDate());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());

            customerRepository.save(existingCustomer);

        });
    }
}
