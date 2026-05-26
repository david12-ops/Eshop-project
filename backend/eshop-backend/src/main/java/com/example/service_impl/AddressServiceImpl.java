package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Address;
import com.example.repository.AddressRepository;
import com.example.service_interface.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Override
    public void saveAddress(Address address) {
        if (address == null)
            return;

        addressRepository.save(address);
    }

    @Override
    public void editAddress(Integer id, Address address) {
        if (id == null || address == null)
            return;

        addressRepository.findById(id).ifPresent(existingAddress -> {

            existingAddress.setStreetName(address.getStreetName());
            existingAddress.setCityName(address.getCityName());
            existingAddress.setStateName(address.getStateName());
            existingAddress.setPostalCode(address.getPostalCode());
            existingAddress.setCountryName(address.getCountryName());

            addressRepository.save(existingAddress);
        });
    }

    @Override
    public void deleteAddressById(Integer id) {
        if (id == null)
            return;

        addressRepository.findById(id).ifPresent(address -> addressRepository.deleteById(id));
    }
}
