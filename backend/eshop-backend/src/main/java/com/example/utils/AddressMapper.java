package com.example.utils;

import org.springframework.stereotype.Component;

import org.springframework.lang.NonNull;

import com.example.dto.AddressDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;

@Component
public class AddressMapper {
    private final RegionMapper regionMapper;

    public AddressMapper(RegionMapper regionMapper) {
        this.regionMapper = regionMapper;
    }

    @NonNull
    public Address mapToAddressEntity(AddressDTO addressDTO) {
        if ((addressDTO.getStreetName() == null || addressDTO.getStreetName().isBlank())
                || (addressDTO.getCityName() == null || addressDTO.getCityName().isBlank())
                || (addressDTO.getStateName() == null || addressDTO.getStateName().isBlank())
                || (addressDTO.getPostalCode() == null || addressDTO.getPostalCode().isBlank())
                || (addressDTO.getCountryName() == null || addressDTO.getCountryName().isBlank())) {
            throw new BadRequestException("Street, city, state, postal code and country are reqired");
        }

        Address address = new Address();

        address.setStreetName(addressDTO.getStreetName());
        address.setCityName(addressDTO.getCityName());
        address.setStateName(addressDTO.getStateName());
        address.setPostalCode(addressDTO.getPostalCode());
        address.setRegion(regionMapper.mapToRegionEntity(addressDTO.getRegionDTO()));
        address.setCountryName(addressDTO.getCountryName());
        address.setDeleted(addressDTO.getDeleted());
        address.setDeletedAt(addressDTO.getDeletedAt());
        address.setCreatedAt(addressDTO.getCreatedAt());
        address.setCreatedBy(addressDTO.getCreatedBy());
        address.setUpdatedAt(addressDTO.getUpdatedAt());
        address.setUpdatedBy(addressDTO.getCreatedBy());

        return address;
    }

    public AddressDTO mapToAddressDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(address.getAddressId());
        addressDTO.setStreetName(address.getStreetName());
        addressDTO.setCityName(address.getCityName());
        addressDTO.setStateName(address.getStateName());
        addressDTO.setPostalCode(address.getPostalCode());
        addressDTO.setRegionDTO(regionMapper.mapToRegionDTO(address.getRegion()));
        addressDTO.setCountryName(address.getCountryName());
        addressDTO.setDeleted(address.getDeleted());
        addressDTO.setDeletedAt(address.getDeletedAt());
        addressDTO.setCreatedAt(address.getCreatedAt());
        addressDTO.setCreatedBy(address.getCreatedBy());
        addressDTO.setUpdatedAt(address.getUpdatedAt());
        addressDTO.setUpdatedBy(address.getUpdatedBy());

        return addressDTO;
    }
}
