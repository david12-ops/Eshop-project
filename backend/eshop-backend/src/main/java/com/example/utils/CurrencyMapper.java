package com.example.utils;

import org.springframework.stereotype.Component;

import org.springframework.lang.NonNull;

import com.example.dto.CurrencyDTO;
import com.example.exception.BadRequestException;
import com.example.model.Currency;

@Component
public class CurrencyMapper {
    @NonNull
    public Currency mapToCurrencyEntity(CurrencyDTO currencyDTO) {
        if ((currencyDTO.getCurrencyCode() == null || currencyDTO.getCurrencyCode().isBlank())
                || (currencyDTO.getCurrencyName() == null || currencyDTO.getCurrencyName().isBlank())) {
            throw new BadRequestException("Code and name are reqired");
        }

        Currency currency = new Currency();

        currency.setCurrencyCode(currency.getCurrencyCode());
        currency.setCurrencyName(currency.getCurrencyName());
        currency.setSymbol(currency.getSymbol());
        currency.setDeleted(currency.getDeleted());
        currency.setDeletedAt(currency.getDeletedAt());
        currency.setCreatedAt(currency.getCreatedAt());
        currency.setCreatedBy(currency.getCreatedBy());
        currency.setUpdatedAt(currency.getUpdatedAt());
        currency.setUpdatedBy(currency.getUpdatedBy());

        return currency;
    }

    public CurrencyDTO mapToCurrencyDTO(Currency currency) {
        CurrencyDTO currencyDTO = new CurrencyDTO();

        currencyDTO.setCurrencyCode(currency.getCurrencyCode());
        currencyDTO.setCurrencyName(currency.getCurrencyName());
        currencyDTO.setSymbol(currency.getSymbol());
        currencyDTO.setDeleted(currency.getDeleted());
        currencyDTO.setDeletedAt(currency.getDeletedAt());
        currencyDTO.setCreatedAt(currency.getCreatedAt());
        currencyDTO.setCreatedBy(currency.getCreatedBy());
        currencyDTO.setUpdatedAt(currency.getUpdatedAt());
        currencyDTO.setUpdatedBy(currency.getUpdatedBy());

        return currencyDTO;
    }
}
