package com.example.utils;

import org.springframework.stereotype.Component;

import com.example.dto.AddressDTO;
import com.example.dto.RegionDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.Region;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class AddressMapperImpl implements ObjectMapper<Address, AddressDTO> {
    private final ObjectMapper<Region, RegionDTO> regionMapper;

    public AddressMapperImpl(ObjectMapper<Region, RegionDTO> regionMapper) {
        this.regionMapper = regionMapper;
    }

    @Override
    public Address mapToEntity(AddressDTO objectDTO) {
        if ((objectDTO.getStreetName() == null || objectDTO.getStreetName().isBlank())
                || (objectDTO.getCityName() == null || objectDTO.getCityName().isBlank())
                || (objectDTO.getStateName() == null || objectDTO.getStateName().isBlank())
                || (objectDTO.getPostalCode() == null || objectDTO.getPostalCode().isBlank())
                || (objectDTO.getCountryName() == null || objectDTO.getCountryName().isBlank())
                || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Street, city, state name, postal code, country name and deletion state are reqired");
        }

        Address address = new Address();

        address.setStreetName(objectDTO.getStreetName());
        address.setCityName(objectDTO.getCityName());
        address.setStateName(objectDTO.getStateName());
        address.setPostalCode(objectDTO.getPostalCode());
        address.setRegion(regionMapper.mapToEntity(objectDTO.getRegionDTO()));
        address.setCountryName(objectDTO.getCountryName());
        address.setDeleted(objectDTO.isDeleted());
        address.setDeletedAt(objectDTO.getDeletedAt());
        address.setCreatedAt(objectDTO.getCreatedAt());
        address.setCreatedBy(objectDTO.getCreatedBy());
        address.setUpdatedAt(objectDTO.getUpdatedAt());
        address.setUpdatedBy(objectDTO.getCreatedBy());

        return address;
    }

    @Override
    public AddressDTO mapToDTO(Address objectEntity) {
        AddressDTO addressDTO = new AddressDTO();

        addressDTO.setAddressId(objectEntity.getAddressId());
        addressDTO.setStreetName(objectEntity.getStreetName());
        addressDTO.setCityName(objectEntity.getCityName());
        addressDTO.setStateName(objectEntity.getStateName());
        addressDTO.setPostalCode(objectEntity.getPostalCode());
        addressDTO.setRegionDTO(regionMapper.mapToDTO(objectEntity.getRegion()));
        addressDTO.setCountryName(objectEntity.getCountryName());
        addressDTO.setDeleted(objectEntity.isDeleted());
        addressDTO.setDeletedAt(objectEntity.getDeletedAt());
        addressDTO.setCreatedAt(objectEntity.getCreatedAt());
        addressDTO.setCreatedBy(objectEntity.getCreatedBy());
        addressDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        addressDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return addressDTO;
    }
}
