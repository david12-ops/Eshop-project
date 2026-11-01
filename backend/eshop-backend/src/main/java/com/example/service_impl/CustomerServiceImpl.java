package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.AddressDTO;
import com.example.dto.AppUserDTO;
import com.example.dto.CustomerDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.AppUser;
import com.example.model.Customer;
import com.example.model.MyUserDetail;
import com.example.repository.CustomerRepository;
import com.example.service_interface.AddressService;
import com.example.service_interface.CustomerService;
import com.example.utils.utils_interface.ObjectMapper;
import com.example.service_interface.AppUserService;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final AddressService addressService;
    private final AppUserService appUserService;

    private final ObjectMapper<Customer, CustomerDTO> customerMapper;
    private final ObjectMapper<Address, AddressDTO> addressMapper;
    private final ObjectMapper<AppUser, AppUserDTO> appUserMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, AddressService addressService,
            AppUserService appUserService, ObjectMapper<Customer, CustomerDTO> customerMapper,
            ObjectMapper<Address, AddressDTO> addressMapper, ObjectMapper<AppUser, AppUserDTO> appUserMapper) {
        this.customerRepository = customerRepository;
        this.addressService = addressService;
        this.appUserService = appUserService;
        this.customerMapper = customerMapper;
        this.addressMapper = addressMapper;
        this.appUserMapper = appUserMapper;
    }

    @Override
    public CustomerDTO getCustomerById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return customerRepository.findById(id).map(customer -> customerMapper.mapToDTO(customer))
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public List<CustomerDTO> getCustomers(MyUserDetail userDetail) {
        return customerRepository.findAll().stream()
                .map(customerMapper::mapToDTO).toList();
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO, MyUserDetail userDetail) {
        if (customerDTO == null) {
            throw new BadRequestException("Customer must not be null");
        }

        customerDTO.setCreatedAt(Instant.now());
        customerDTO.setUpdatedAt(Instant.now());

        customerDTO.setCreatedBy(userDetail.getUserId());
        customerDTO.setUpdatedBy(userDetail.getUserId());

        customerDTO.setDeleted(false);
        customerDTO.setDeletedAt(null);

        Customer savedCustomer = customerRepository.save(customerMapper.mapToEntity(customerDTO));

        return customerMapper.mapToDTO(savedCustomer);
    }

    // Soft deletion
    @Override
    public void deleteCustomerById(Integer id, MyUserDetail userDetail) {
        Customer customerToDelete = getCustomerEntityById(id);

        if (customerToDelete.isDeleted())
            return;

        customerToDelete.setUpdatedAt(Instant.now());
        customerToDelete.setUpdatedBy(userDetail.getUserId());

        customerToDelete.setDeleted(true);
        customerToDelete.setDeletedAt(Instant.now());

        customerRepository.save(customerToDelete);
        // customerRepository.softDeleteById(existingCustomer.getCustomerId());
    }

    @Override
    public CustomerDTO editCustomer(Integer id, CustomerDTO customerDTO, MyUserDetail userDetail) {
        if (customerDTO == null) {
            throw new BadRequestException("Customer to edit must not be null");
        }

        Customer customerToUpdate = getCustomerEntityById(id);

        customerToUpdate.setBranchName(customerDTO.getBranchName());
        customerToUpdate.setFirstName(customerDTO.getFirstName());
        customerToUpdate.setLastName(customerDTO.getLastName());
        customerToUpdate.setBirthDate(customerDTO.getBirthDate());
        customerToUpdate.setPhoneNumber(customerDTO.getPhoneNumber());
        customerToUpdate.setUpdatedAt(Instant.now());
        customerToUpdate.setUpdatedBy(userDetail.getUserId());
        customerToUpdate.setDeleted(false);
        customerToUpdate.setDeletedAt(null);

        if (customerDTO.getCustomerUserDTO() != null
                && customerDTO.getCustomerUserDTO().getAppUserId() != null) {

            AppUser appUser = appUserMapper.mapToEntity(appUserService.getAppUserById(
                    customerDTO.getCustomerUserDTO().getAppUserId(), userDetail));

            customerToUpdate.setCustomerUser(appUser);

        } else {

            customerToUpdate.setCustomerUser(null);
        }

        if (customerDTO.getCustomerAddressDTO() != null
                && customerDTO.getCustomerAddressDTO().getAddressId() != null) {

            Address adddress = addressMapper
                    .mapToEntity(addressService.getAddressById(customerDTO.getCustomerAddressDTO().getAddressId(),
                            userDetail));

            customerToUpdate.setCustomerAddress(adddress);
        } else {

            customerToUpdate.setCustomerUser(null);
        }

        return customerMapper.mapToDTO(customerRepository.save(customerToUpdate));
    }

    private Customer getCustomerEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }
}
