package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.AddressDTO;
import com.example.dto.RegionDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.MyUserDetail;
import com.example.model.Region;
import com.example.repository.AddressRepository;
import com.example.service_interface.AddressService;
import com.example.service_interface.RegionService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final RegionService regionService;

    private final ObjectMapper<Address, AddressDTO> addressMapper;
    private final ObjectMapper<Region, RegionDTO> regionMapper;

    public AddressServiceImpl(AddressRepository addressRepository, RegionService regionService,
            ObjectMapper<Address, AddressDTO> addressMapper, ObjectMapper<Region, RegionDTO> regionMapper) {
        this.addressRepository = addressRepository;
        this.regionService = regionService;
        this.addressMapper = addressMapper;
        this.regionMapper = regionMapper;
    }

    @Override
    public List<AddressDTO> getAddresses(MyUserDetail userDetail) {
        return addressRepository.findAll().stream().map(address -> addressMapper.mapToDTO(address)).toList();
    }

    @Override
    public AddressDTO getAddressById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return addressRepository.findById(id).map(address -> {
            AddressDTO mappedAddressDTO = addressMapper.mapToDTO(address);

            mappedAddressDTO.setAddressId(id);

            return mappedAddressDTO;
        })
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO, MyUserDetail userDetail) {
        if (addressDTO == null) {
            throw new BadRequestException("Address must not be null");
        }

        addressDTO.setDeleted(false);
        addressDTO.setDeletedAt(null);

        if (addressDTO.getCreatedAt() == null) {
            addressDTO.setCreatedAt(Instant.now());
        }

        if (addressDTO.getUpdatedAt() == null) {
            addressDTO.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (addressDTO.getCreatedBy() == null) {
                addressDTO.setCreatedBy(userDetail.getUserId());
            }

            if (addressDTO.getUpdatedBy() == null) {
                addressDTO.setUpdatedBy(userDetail.getUserId());
            }
        }

        Address newAddress = addressMapper.mapToEntity(addressDTO);

        Address savedAddress = addressRepository.save(newAddress);

        return addressMapper.mapToDTO(savedAddress);
    }

    @Override
    public AddressDTO editAddress(Integer id, AddressDTO addressDTO, MyUserDetail userDetail) {
        if (addressDTO == null) {
            throw new BadRequestException("Address to edit must not be null");
        }

        // zde u vsech hlidani prav

        Address addressToUpdate = getAddressEntityById(id);

        addressToUpdate.setStreetName(addressDTO.getStreetName());
        addressToUpdate.setCityName(addressDTO.getCityName());
        addressToUpdate.setStateName(addressDTO.getStateName());
        addressToUpdate.setPostalCode(addressDTO.getPostalCode());
        addressToUpdate.setCountryName(addressDTO.getCountryName());
        addressToUpdate.setDeleted(false);
        addressToUpdate.setDeletedAt(null);

        if (addressDTO.getRegionDTO() != null
                && addressDTO.getRegionDTO().getRegionId() != null) {

            Region region = regionMapper.mapToEntity(regionService.getRegionById(
                    addressDTO.getRegionDTO().getRegionId(), userDetail));

            addressToUpdate.setRegion(region);

        } else {

            addressToUpdate.setRegion(null);
        }

        if (addressDTO.getUpdatedAt() == null) {
            addressToUpdate.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (addressDTO.getCreatedBy() == null) {
                addressToUpdate.setCreatedBy(userDetail.getUserId());
            }

            if (addressDTO.getUpdatedBy() == null) {
                addressToUpdate.setUpdatedBy(userDetail.getUserId());
            }
        }

        Address updatedAddress = addressRepository.save(addressToUpdate);

        return addressMapper.mapToDTO(updatedAddress);
    }

    // Soft deletion
    @Override
    public void deleteAddressById(Integer id, MyUserDetail userDetail) {
        Address existingAddress = getAddressEntityById(id);

        if (existingAddress.isDeleted())
            return;

        addressRepository.softDeleteById(existingAddress.getAddressId());
    }

    private Address getAddressEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }
}
