package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.CurrencyDTO;
import com.example.dto.RegionDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.MyUserDetail;
import com.example.model.Region;
import com.example.model.Currency;
import com.example.repository.RegionRepository;
import com.example.service_interface.CurrencyService;
import com.example.service_interface.RegionService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final CurrencyService currencyService;

    private final ObjectMapper<Region, RegionDTO> regionMapper;
    private final ObjectMapper<Currency, CurrencyDTO> currencyMapper;

    public RegionServiceImpl(RegionRepository regionRepository, CurrencyService currencyService,
            ObjectMapper<Region, RegionDTO> regionMapper, ObjectMapper<Currency, CurrencyDTO> currencyMapper) {
        this.regionRepository = regionRepository;
        this.currencyService = currencyService;
        this.regionMapper = regionMapper;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public RegionDTO getRegionById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return regionRepository.findById(id).map(region -> regionMapper.mapToDTO(region))
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + id));
    }

    @Override
    public List<RegionDTO> getRegions(MyUserDetail userDetail) {
        return regionRepository.findAll().stream()
                .map(regionMapper::mapToDTO).toList();
    }

    @Override
    public RegionDTO saveRegion(RegionDTO regionDTO, MyUserDetail userDetail) {
        if (regionDTO == null) {
            throw new BadRequestException("Region must not be null");
        }

        regionDTO.setCreatedAt(Instant.now());
        regionDTO.setUpdatedAt(Instant.now());

        regionDTO.setCreatedBy(userDetail.getUserId());
        regionDTO.setUpdatedBy(userDetail.getUserId());

        regionDTO.setDeleted(false);
        regionDTO.setDeletedAt(null);

        Region savedRegion = regionRepository.save(regionMapper.mapToEntity(regionDTO));

        return regionMapper.mapToDTO(savedRegion);
    }

    // Soft deletion
    @Override
    public void deleteRegionById(Integer id, MyUserDetail userDetail) {
        Region regionToDelete = getRegionEntityById(id);

        if (regionToDelete.isDeleted())
            return;

        regionToDelete.setUpdatedAt(Instant.now());
        regionToDelete.setUpdatedBy(userDetail.getUserId());

        regionToDelete.setDeleted(true);
        regionToDelete.setDeletedAt(Instant.now());

        regionRepository.save(regionToDelete);
        // regionRepository.softDeleteById(regionToDelete.getRegionId());
    }

    @Override
    public RegionDTO editRegion(Integer id, RegionDTO regionDTO, MyUserDetail userDetail) {
        if (regionDTO == null) {
            throw new BadRequestException("Region to edit must not be null");
        }

        Region regionToUpdate = getRegionEntityById(id);

        regionToUpdate.setRegionName(regionDTO.getRegionName());
        regionToUpdate.setUpdatedAt(Instant.now());
        regionToUpdate.setUpdatedBy(userDetail.getUserId());
        regionToUpdate.setDeleted(false);
        regionToUpdate.setDeletedAt(null);

        if (regionDTO.getCurrencyDTO() != null
                && regionDTO.getCurrencyDTO().getCurrencyCode() != null) {

            Currency currency = currencyMapper.mapToEntity(currencyService.getCurrencyByCode(
                    regionDTO.getCurrencyDTO().getCurrencyCode(), userDetail));

            regionToUpdate.setCurrency(currency);

        } else {

            regionToUpdate.setCurrency(null);
        }

        return regionMapper.mapToDTO(regionRepository.save(regionToUpdate));
    }

    private Region getRegionEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + id));
    }
}
