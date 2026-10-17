package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.RegionDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.MyUserDetail;
import com.example.model.Region;
import com.example.model.Currency;
import com.example.repository.RegionRepository;
import com.example.service_interface.CurrencyService;
import com.example.service_interface.RegionService;
import com.example.utils.CurrencyMapper;
import com.example.utils.RegionMapper;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final CurrencyService currencyService;

    private final RegionMapper regionMapper;
    private final CurrencyMapper currencyMapper;

    public RegionServiceImpl(RegionRepository regionRepository, CurrencyService currencyService,
            RegionMapper regionMapper, CurrencyMapper currencyMapper) {
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

        return regionRepository.findById(id).map(region -> {
            RegionDTO mappedRegionDTO = regionMapper.mapToRegionDTO(region);

            mappedRegionDTO.setRegionId(id);

            return mappedRegionDTO;
        })
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + id));
    }

    @Override
    public List<RegionDTO> getRegions(MyUserDetail userDetail) {
        return regionRepository.findAll().stream().map(region -> regionMapper.mapToRegionDTO(null)).toList();
    }

    @Override
    public RegionDTO saveRegion(RegionDTO regionDTO, MyUserDetail userDetail) {
        if (regionDTO == null) {
            throw new BadRequestException("Region must not be null");
        }

        regionDTO.setDeleted(false);
        regionDTO.setDeletedAt(null);

        if (regionDTO.getCreatedAt() == null) {
            regionDTO.setCreatedAt(Instant.now());
        }

        if (regionDTO.getUpdatedAt() == null) {
            regionDTO.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (regionDTO.getCreatedBy() == null) {
                regionDTO.setCreatedBy(userDetail.getUserId());
            }

            if (regionDTO.getUpdatedBy() == null) {
                regionDTO.setUpdatedBy(userDetail.getUserId());
            }
        }

        Region newRegion = regionMapper.mapToRegionEntity(regionDTO);

        Region savedRegion = regionRepository.save(newRegion);

        return regionMapper.mapToRegionDTO(savedRegion);
    }

    // Soft deletion
    @Override
    public void deleteRegionById(Integer id, MyUserDetail userDetail) {
        Region existingRegion = getRegionEntityById(id);

        if (existingRegion.getDeleted())
            return;

        regionRepository.softDeleteById(existingRegion.getRegionId());
    }

    @Override
    public RegionDTO editRegion(Integer id, RegionDTO regionDTO, MyUserDetail userDetail) {
        if (regionDTO == null) {
            throw new BadRequestException("Region to edit must not be null");
        }

        Region regionToUpdate = getRegionEntityById(id);

        regionToUpdate.setRegionName(regionDTO.getRegionName());
        regionToUpdate.setDeleted(false);
        regionToUpdate.setDeletedAt(null);

        if (regionDTO.getCurrencyDTO() != null
                && regionDTO.getCurrencyDTO().getCurrencyCode() != null) {

            Currency currency = currencyMapper.mapToCurrencyEntity(currencyService.getCurrencyByCode(
                    regionDTO.getCurrencyDTO().getCurrencyCode(), userDetail));

            regionToUpdate.setCurrency(currency);

        } else {

            regionToUpdate.setCurrency(null);
        }

        if (regionDTO.getUpdatedAt() == null) {
            regionToUpdate.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (regionDTO.getCreatedBy() == null) {
                regionToUpdate.setCreatedBy(userDetail.getUserId());
            }

            if (regionDTO.getUpdatedBy() == null) {
                regionToUpdate.setUpdatedBy(userDetail.getUserId());
            }
        }

        Region updatedRegion = regionRepository.save(regionToUpdate);

        return regionMapper.mapToRegionDTO(updatedRegion);
    }

    private Region getRegionEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return regionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Region not found with id: " + id));
    }
}
