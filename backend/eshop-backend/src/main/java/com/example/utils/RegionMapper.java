package com.example.utils;

import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

import com.example.dto.RegionDTO;
import com.example.exception.BadRequestException;
import com.example.model.Region;

@Component
public class RegionMapper {
    private final CurrencyMapper currencyMapper;

    public RegionMapper(CurrencyMapper currencyMapper) {
        this.currencyMapper = currencyMapper;
    }

    @NonNull
    public Region mapToRegionEntity(RegionDTO regionDTO) {
        if ((regionDTO.getRegionName() == null || regionDTO.getRegionName().isBlank())
                || (regionDTO.getCurrencyDTO() == null)) {
            throw new BadRequestException("Region name, city, state, postal code and country are reqired");
        }

        Region region = new Region();

        region.setRegionName(regionDTO.getRegionName());
        region.setCurrency(currencyMapper.mapToCurrencyEntity(regionDTO.getCurrencyDTO()));
        region.setDeleted(regionDTO.getDeleted());
        region.setDeletedAt(regionDTO.getDeletedAt());
        region.setCreatedAt(regionDTO.getCreatedAt());
        region.setCreatedBy(regionDTO.getCreatedBy());
        region.setUpdatedAt(regionDTO.getUpdatedAt());
        region.setUpdatedBy(regionDTO.getUpdatedBy());

        return region;
    }

    public RegionDTO mapToRegionDTO(Region region) {
        RegionDTO regionDTO = new RegionDTO();

        regionDTO.setRegionId(region.getRegionId());
        regionDTO.setRegionName(region.getRegionName());
        regionDTO.setCurrencyDTO(currencyMapper.mapToCurrencyDTO(region.getCurrency()));
        regionDTO.setDeleted(region.getDeleted());
        regionDTO.setDeletedAt(region.getDeletedAt());
        regionDTO.setCreatedAt(region.getCreatedAt());
        regionDTO.setCreatedBy(region.getCreatedBy());
        regionDTO.setUpdatedAt(region.getUpdatedAt());
        regionDTO.setUpdatedBy(region.getUpdatedBy());

        return regionDTO;
    }
}
