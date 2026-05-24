package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

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
    public Currency getCurrencyById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrencyById'");
    }

    @Override
    public List<Currency> getAllCurrencies() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllCurrencies'");
    }

    @Override
    public void saveCurrency(Currency currency) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveCurrency'");
    }

    @Override
    public void deleteCurrencyById(String code) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteCurrencyById'");
    }

}
