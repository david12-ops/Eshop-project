package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
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

            if (address.getRegion() != null
                    && address.getRegion().getRegionId() != null) {

                Region region = regionService.getRegionById(
                        address.getRegion().getRegionId());

                existingAddress.setRegion(region);

            } else {

                existingAddress.setRegion(null);
            }

            addressRepository.save(existingAddress);
        });
    }

    @Override
    public void deleteAddressById(Integer id) {
        if (id == null)
            return;

        // zatím ne
        addressRepository.findById(id).ifPresent(address -> {
            if (address.getDeleted())
                return; // tady jen soft kvůli klíčům

            addressRepository.softDeleteById(id);
        });
    }
}
