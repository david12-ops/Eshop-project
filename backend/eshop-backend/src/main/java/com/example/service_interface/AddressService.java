package com.example.service_interface;

import java.util.List;

import com.example.model.Address;

public interface AddressService {

    List<Address> getAddresses();

    Address getAddressById(Integer id);

    Address saveAddress(Address address);

    Address editAddress(Integer id, Address address);

    void deleteAddressById(Integer id);
}