package com.example.service_interface;

import java.util.List;

import com.example.model.Currency;

public interface CurrencyService {

    Currency getCurrencyById(String code);

    List<Currency> getCurrencies();

    Currency saveCurrency(Currency currency);

    Currency editCurrency(String code, Currency currency);

    void deleteCurrencyById(String code);
}
