package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Currency;
import com.example.service_interface.CurrencyService;

@RestController 
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    // LIST
    @GetMapping
    public List<Currency> listCurrencies() {
        return currencyService.getCurrencies();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Currency currencyDetail(@PathVariable String id) {
        return currencyService.getCurrencyById(id);
    }

    // CREATE
    @PostMapping("/create")
    public Currency createCurrency(@RequestBody Currency currency) {
        return currencyService.saveCurrency(currency);
    }

    // EDIT
    @PutMapping("/{id}")
    public Currency editCurrency(
            @PathVariable String id,
            @RequestBody Currency currency) {
        return currencyService.editCurrency(id, currency);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteCurrency(
            @PathVariable String id) {
        currencyService.deleteCurrencyById(id);
    }
}
