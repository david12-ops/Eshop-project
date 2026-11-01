package com.example.utils;

import com.example.dto.AddressDTO;
import com.example.dto.AppUserDTO;
import com.example.dto.CustomerDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.AppUser;
import com.example.model.Customer;
import com.example.utils.utils_interface.ObjectMapper;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapperImpl implements ObjectMapper<Customer, CustomerDTO> {
    private final ObjectMapper<Address, AddressDTO> addressMapper;
    private final ObjectMapper<AppUser, AppUserDTO> appUserMapper;

    public CustomerMapperImpl(ObjectMapper<Address, AddressDTO> addressMapper,
            ObjectMapper<AppUser, AppUserDTO> appUserMapper) {
        this.addressMapper = addressMapper;
        this.appUserMapper = appUserMapper;
    }

    @Override
    @NonNull
    public Customer mapToEntity(CustomerDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if (((objectDTO.getBranchName() == null || objectDTO.getBranchName().isBlank())
                && (objectDTO.getFirstName() == null || objectDTO.getFirstName().isBlank())
                && (objectDTO.getLastName() == null || objectDTO.getLastName().isBlank())) ||
                objectDTO.getBirthDate() == null
                || (objectDTO.getPhoneNumber() == null || objectDTO.getPhoneNumber().isBlank())
                || objectDTO.getCustomerAddressDTO() == null || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Name (branch or first and last name), birth date, phone number, customer address and deletion state are required");
        }

        Customer customer = new Customer();

        customer.setBranchName(objectDTO.getBranchName());
        customer.setFirstName(objectDTO.getFirstName());
        customer.setLastName(objectDTO.getLastName());
        customer.setBirthDate(objectDTO.getBirthDate());
        customer.setPhoneNumber(objectDTO.getPhoneNumber());
        customer.setCustomerAddress(addressMapper.mapToEntity(objectDTO.getCustomerAddressDTO()));
        customer.setCustomerUser(appUserMapper.mapToEntity(objectDTO.getCustomerUserDTO()));
        customer.setDeleted(objectDTO.isDeleted());
        customer.setDeletedAt(objectDTO.getDeletedAt());
        customer.setCreatedAt(objectDTO.getCreatedAt());
        customer.setUpdatedAt(objectDTO.getUpdatedAt());
        customer.setCreatedBy(objectDTO.getCreatedBy());
        customer.setUpdatedBy(objectDTO.getUpdatedBy());

        return customer;
    }

    @Override
    public CustomerDTO mapToDTO(Customer objectEntity) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerId(objectEntity.getCustomerId());
        customerDTO.setBranchName(objectEntity.getBranchName());
        customerDTO.setFirstName(objectEntity.getFirstName());
        customerDTO.setLastName(objectEntity.getLastName());
        customerDTO.setBirthDate(objectEntity.getBirthDate());
        customerDTO.setPhoneNumber(objectEntity.getPhoneNumber());
        customerDTO.setCustomerAddressDTO(addressMapper.mapToDTO(objectEntity.getCustomerAddress()));
        customerDTO.setCustomerUserDTO(appUserMapper.mapToDTO(objectEntity.getCustomerUser()));
        customerDTO.setDeleted(objectEntity.isDeleted());
        customerDTO.setDeletedAt(objectEntity.getDeletedAt());
        customerDTO.setCreatedAt(objectEntity.getCreatedAt());
        customerDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        customerDTO.setCreatedBy(objectEntity.getCreatedBy());
        customerDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return customerDTO;
    }
}
