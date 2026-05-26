package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Currency;
import com.example.service_interface.CurrencyService;

@Controller
@RequestMapping("/currencies")
public class CurrencyController {
    private final CurrencyService currencyService;

    public CurrencyController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    // LIST
    @GetMapping
    public String listCurrencies(Model model) {

        model.addAttribute("currencies", currencyService.getAllCurrencies());

        return "currencies/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String currencyDetail(
            @PathVariable String id,
            Model model) {

        Currency currency = currencyService.getCurrencyById(id);

        model.addAttribute(
                "currency",
                currency);

        return "currencies/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "currency",
                new Currency());

        return "currencies/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createCurrency(
            @ModelAttribute Currency currency) {

        currencyService.saveCurrency(currency);

        return "redirect:/currencies";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable String id,
            Model model) {

        Currency currency = currencyService.getCurrencyById(id);

        model.addAttribute(
                "currency",
                currency);

        return "currencies/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editCurrency(
            @PathVariable String id,
            @ModelAttribute Currency currency) {

        currencyService.editCurrency(id, currency);

        return "redirect:/currencies";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteCurrency(
            @PathVariable String code) {

        currencyService.deleteCurrencyById(code);

        return "redirect:/currencies";
    }
}
