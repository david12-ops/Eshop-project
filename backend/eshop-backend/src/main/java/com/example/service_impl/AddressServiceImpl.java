package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.Region;
import com.example.repository.AddressRepository;
import com.example.service_interface.AddressService;
import com.example.service_interface.RegionService;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final RegionService regionService;

    public AddressServiceImpl(AddressRepository addressRepository, RegionService regionService) {
        this.addressRepository = addressRepository;
        this.regionService = regionService;
    }

    @Override
    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    @Override
    public Address getAddressById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Override
    public Address saveAddress(Address address) {
        if (address == null) {
            throw new BadRequestException("Address must not be null");
        }

        return addressRepository.save(address);
    }

    @Override
    public Address editAddress(Integer id, Address address) {
        if (address == null) {
            throw new BadRequestException("Address to edit must not be null");
        }

        Address existingAddress = getAddressById(id);

        existingAddress.setStreetName(address.getStreetName());
        existingAddress.setCityName(address.getCityName());
        existingAddress.setStateName(address.getStateName());
        existingAddress.setPostalCode(address.getPostalCode());
        existingAddress.setCountryName(address.getCountryName());

        if (address.getRegion() != null
                && address.getRegion().getRegionId() != null) {

            Region region = regionService.getRegionById(
                    address.getRegion().getRegionId());

            existingAddress.setRegion(region);

        } else {

            existingAddress.setRegion(null);
        }

        return addressRepository.save(existingAddress);
    }

    // Soft deletion
    @Override
    public void deleteAddressById(Integer id) {
        Address existingAddress = getAddressById(id);

        if (existingAddress.getDeleted())
            return;

        addressRepository.softDeleteById(existingAddress.getAddressId());
    }
}
