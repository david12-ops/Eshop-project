package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Currency;
import com.example.repository.CurrencyRepository;
import com.example.service_interface.CurrencyService;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository) {
        this.currencyRepository = currencyRepository;
    }

    @Override
    public Currency getCurrencyById(String code) {
        if (code == null) {
            throw new BadRequestException("Code must not be null");
        }

        return currencyRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with code: " + code));
    }

    @Override
    public List<Currency> getCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public Currency saveCurrency(Currency currency) {
        if (currency == null) {
            throw new BadRequestException("Currency must not be null");
        }

        return currencyRepository.save(currency);
    }

    // Soft deletion
    @Override
    public void deleteCurrencyById(String code) {
        if (code == null) {
            throw new BadRequestException("Code must not be null");
        }

        currencyRepository.findById(code).ifPresent(currency -> {
            if (currency.getDeleted())
                return;

            currencyRepository.softDeleteById(code);
        });
    }

    @Override
    public Currency editCurrency(String code, Currency currency) {
        if (code == null || currency == null) {
            throw new BadRequestException("Code or currency to edit must not be null");
        }

        return currencyRepository.findById(code).map(existingCurrency -> {
            existingCurrency.setCurrencyName(currency.getCurrencyName());
            existingCurrency.setSymbol(currency.getSymbol());

            return currencyRepository.save(existingCurrency);
        }).orElse(null);
    }
}
