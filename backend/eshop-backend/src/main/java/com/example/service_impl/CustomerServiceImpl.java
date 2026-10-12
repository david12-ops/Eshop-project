package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.AppUser;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.service_interface.AddressService;
import com.example.service_interface.CustomerService;
import com.example.service_interface.AppUserService;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final AppUserService appUserService;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressService addressService,
            AppUserService appUserService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.appUserService = appUserService;
    }

    @Override
    public Customer getCustomerById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Customer saveCustomer(Customer customer) {
        if (customer == null) {
            throw new BadRequestException("Customer must not be null");
        }

        return customerRepository.save(customer);
    }

    // Soft deletion
    @Override
    public void deleteCustomerById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        customerRepository.findById(id).ifPresent(customer -> {
            if (customer.getDeleted())
                return;

            customerRepository.softDeleteById(id);
        });
    }

    @Override
    public Customer editCustomer(Integer id, Customer customer) {
        if (id == null || customer == null) {
            throw new BadRequestException("ID or customer to edit must not be null");
        }

        return customerRepository.findById(id).map(existingCustomer -> {
            Address adddress = addressService.getAddressById(customer.getCustomerAddress().getId());

            existingCustomer.setBranchName(customer.getBranchName());
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setBirthDate(customer.getBirthDate());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            existingCustomer.setCustomerAddress(adddress);

            if (customer.getCustomerUser() != null
                    && customer.getCustomerUser().getId() != null) {

                AppUser appUser = appUserService.getAppUserById(
                        customer.getCustomerUser().getId());

                existingCustomer.setCustomerUser(appUser);

            } else {

                existingCustomer.setCustomerUser(null);
            }

            return customerRepository.save(existingCustomer);
        }).orElse(null);
    }
}
