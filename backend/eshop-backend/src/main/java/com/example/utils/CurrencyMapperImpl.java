package com.example.utils;

import org.springframework.stereotype.Component;

import com.example.dto.CurrencyDTO;
import com.example.exception.BadRequestException;
import com.example.model.Currency;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class CurrencyMapperImpl implements ObjectMapper<Currency, CurrencyDTO> {

    @Override
    public Currency mapToEntity(CurrencyDTO objectDTO) {
        if ((objectDTO.getCurrencyCode() == null || objectDTO.getCurrencyCode().isBlank())
                || (objectDTO.getCurrencyName() == null || objectDTO.getCurrencyName().isBlank())
                || objectDTO.isDeleted() == null) {
            throw new BadRequestException("Code, name and deletion state are reqired");
        }

        Currency currency = new Currency();

        currency.setCurrencyCode(objectDTO.getCurrencyCode());
        currency.setCurrencyName(objectDTO.getCurrencyName());
        currency.setSymbol(objectDTO.getSymbol());
        currency.setDeleted(objectDTO.isDeleted());
        currency.setDeletedAt(objectDTO.getDeletedAt());
        currency.setCreatedAt(objectDTO.getCreatedAt());
        currency.setCreatedBy(objectDTO.getCreatedBy());
        currency.setUpdatedAt(objectDTO.getUpdatedAt());
        currency.setUpdatedBy(objectDTO.getUpdatedBy());

        return currency;
    }

    @Override
    public CurrencyDTO mapToDTO(Currency objectEntity) {
        CurrencyDTO currencyDTO = new CurrencyDTO();

        currencyDTO.setCurrencyCode(objectEntity.getCurrencyCode());
        currencyDTO.setCurrencyName(objectEntity.getCurrencyName());
        currencyDTO.setSymbol(objectEntity.getSymbol());
        currencyDTO.setDeleted(objectEntity.isDeleted());
        currencyDTO.setDeletedAt(objectEntity.getDeletedAt());
        currencyDTO.setCreatedAt(objectEntity.getCreatedAt());
        currencyDTO.setCreatedBy(objectEntity.getCreatedBy());
        currencyDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        currencyDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return currencyDTO;
    }
}
