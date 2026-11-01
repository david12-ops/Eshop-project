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
        return addressRepository.findAll().stream()
                .map(addressMapper::mapToDTO).toList();
    }

    @Override
    public AddressDTO getAddressById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return addressRepository.findById(id).map(address -> addressMapper.mapToDTO(address))
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }

    @Override
    public AddressDTO saveAddress(AddressDTO addressDTO, MyUserDetail userDetail) {
        if (addressDTO == null) {
            throw new BadRequestException("Address must not be null");
        }

        addressDTO.setCreatedAt(Instant.now());
        addressDTO.setUpdatedAt(Instant.now());

        addressDTO.setCreatedBy(userDetail.getUserId());
        addressDTO.setUpdatedBy(userDetail.getUserId());

        addressDTO.setDeleted(false);
        addressDTO.setDeletedAt(null);

        Address savedAddress = addressRepository.save(addressMapper.mapToEntity(addressDTO));

        return addressMapper.mapToDTO(savedAddress);
    }

    @Override
    public AddressDTO editAddress(Integer id, AddressDTO addressDTO, MyUserDetail userDetail) {
        if (addressDTO == null) {
            throw new BadRequestException("Address to edit must not be null");
        }

        Address addressToUpdate = getAddressEntityById(id);

        addressToUpdate.setStreetName(addressDTO.getStreetName());
        addressToUpdate.setCityName(addressDTO.getCityName());
        addressToUpdate.setStateName(addressDTO.getStateName());
        addressToUpdate.setPostalCode(addressDTO.getPostalCode());
        addressToUpdate.setCountryName(addressDTO.getCountryName());
        addressToUpdate.setUpdatedAt(Instant.now());
        addressToUpdate.setUpdatedBy(userDetail.getUserId());
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

        return addressMapper.mapToDTO(addressRepository.save(addressToUpdate));
    }

    // Soft deletion
    @Override
    public void deleteAddressById(Integer id, MyUserDetail userDetail) {
        Address addressToDelete = getAddressEntityById(id);

        if (addressToDelete.isDeleted())
            return;

        addressToDelete.setUpdatedAt(Instant.now());
        addressToDelete.setUpdatedBy(userDetail.getUserId());

        addressToDelete.setDeleted(true);
        addressToDelete.setDeletedAt(Instant.now());

        addressRepository.save(addressToDelete);
        // addressRepository.softDeleteById(existingAddress.getAddressId());
    }

    private Address getAddressEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with id: " + id));
    }
}
