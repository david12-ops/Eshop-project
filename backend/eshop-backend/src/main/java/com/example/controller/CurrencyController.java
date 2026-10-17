package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CurrencyDTO;
import com.example.model.MyUserDetail;
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
    public List<CurrencyDTO> listCurrencies(@AuthenticationPrincipal MyUserDetail userDetail) {
        return currencyService.getCurrencies(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public CurrencyDTO currencyDetail(
            @PathVariable String code,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return currencyService.getCurrencyByCode(code, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public CurrencyDTO createCurrency(
            @RequestBody CurrencyDTO currencyDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return currencyService.saveCurrency(currencyDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public CurrencyDTO editCurrency(
            @PathVariable String id,
            @RequestBody CurrencyDTO currencyDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return currencyService.editCurrency(id, currencyDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteCurrency(@PathVariable String id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        currencyService.deleteCurrencyById(id, userDetail);
    }
}
