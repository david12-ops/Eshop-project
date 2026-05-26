package com.example.service_interface;

import java.util.List;

import com.example.model.Address;

public interface AddressService {

    List<Address> getAllAddresses();

    Address getAddressById(Integer id);

    void saveAddress(Address address);

    void editAddress(Integer id, Address address);

    void deleteAddressById(Integer id);
}