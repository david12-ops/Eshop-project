package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Address;
import com.example.model.AppUser;
import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.service_interface.AddressService;
import com.example.service_interface.CustomerService;
import com.example.service_interface.UserService;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final UserService userService;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressService addressService,
            UserService userService) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.userService = userService;
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

        // zatím ne
        customerRepository.findById(id).ifPresent(customer -> {
            if (customer.getDeleted())
                return; // tady jen soft kvůli klíčům

            customerRepository.softDeleteById(id);
        });
    }

    @Override
    public void editCustomer(Integer id, Customer customer) {
        if (id == null || customer == null)
            return;

        customerRepository.findById(id).ifPresent(existingCustomer -> {
            Address adddress = addressService.getAddressById(customer.getCustomerAddress().getId());

            existingCustomer.setBranchName(customer.getBranchName());
            existingCustomer.setFirstName(customer.getFirstName());
            existingCustomer.setLastName(customer.getLastName());
            existingCustomer.setBirthDate(customer.getBirthDate());
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
            existingCustomer.setCustomerAddress(adddress);

            if (customer.getCustomerUser() != null
                    && customer.getCustomerUser().getId() != null) {

                AppUser appUser = userService.getUserById(
                        customer.getCustomerUser().getId());

                existingCustomer.setCustomerUser(appUser);

            } else {

                existingCustomer.setCustomerUser(null);
            }

            customerRepository.save(existingCustomer);
        });
    }
}
