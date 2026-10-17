package com.example.utils;

import org.springframework.stereotype.Component;

import com.example.dto.CurrencyDTO;
import com.example.dto.RegionDTO;
import com.example.exception.BadRequestException;
import com.example.model.Currency;
import com.example.model.Region;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class RegionMapperImpl implements ObjectMapper<Region, RegionDTO> {
    private final ObjectMapper<Currency, CurrencyDTO> currencyMapper;

    public RegionMapperImpl(ObjectMapper<Currency, CurrencyDTO> currencyMapper) {
        this.currencyMapper = currencyMapper;
    }

    @Override
    public Region mapToEntity(RegionDTO objectDTO) {
        if ((objectDTO.getRegionName() == null || objectDTO.getRegionName().isBlank())
                || objectDTO.isDeleted() == null
                || objectDTO.getCurrencyDTO() == null) {
            throw new BadRequestException("Region name, deletion state and currency are reqired");
        }

        Region region = new Region();

        region.setRegionName(objectDTO.getRegionName());
        region.setCurrency(currencyMapper.mapToEntity(objectDTO.getCurrencyDTO()));
        region.setDeleted(objectDTO.isDeleted());
        region.setDeletedAt(objectDTO.getDeletedAt());
        region.setCreatedAt(objectDTO.getCreatedAt());
        region.setCreatedBy(objectDTO.getCreatedBy());
        region.setUpdatedAt(objectDTO.getUpdatedAt());
        region.setUpdatedBy(objectDTO.getUpdatedBy());

        return region;
    }

    @Override
    public RegionDTO mapToDTO(Region objectEntity) {
        RegionDTO regionDTO = new RegionDTO();

        regionDTO.setRegionId(objectEntity.getRegionId());
        regionDTO.setRegionName(objectEntity.getRegionName());
        regionDTO.setCurrencyDTO(currencyMapper.mapToDTO(objectEntity.getCurrency()));
        regionDTO.setDeleted(objectEntity.isDeleted());
        regionDTO.setDeletedAt(objectEntity.getDeletedAt());
        regionDTO.setCreatedAt(objectEntity.getCreatedAt());
        regionDTO.setCreatedBy(objectEntity.getCreatedBy());
        regionDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        regionDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return regionDTO;
    }
}
