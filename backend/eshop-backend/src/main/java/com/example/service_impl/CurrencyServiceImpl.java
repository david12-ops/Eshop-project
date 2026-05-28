package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
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
        return currencyRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with code: " + code));
    }

    @Override
    public List<Currency> getAllCurrencies() {
        return currencyRepository.findAll();
    }

    @Override
    public void saveCurrency(Currency currency) {
        if (currency == null)
            return;

        currencyRepository.save(currency);
    }

    @Override
    public void deleteCurrencyById(String code) {
        if (code == null)
            return;

        // zatím ne
        currencyRepository.findById(code).ifPresent(currency -> {
            if (currency.getDeleted())
                return; // tady jen soft kvůli klíčům

            currencyRepository.softDeleteById(code);
        });
    }

    @Override
    public void editCurrency(String code, Currency currency) {
        if (code == null || currency == null)
            return;

        currencyRepository.findById(code).ifPresent(existingCurrency -> {

            existingCurrency.setCurrencyName(currency.getCurrencyName());
            existingCurrency.setSymbol(currency.getSymbol());

            currencyRepository.save(existingCurrency);

        });
    }
}
