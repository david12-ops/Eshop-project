package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Address;
import com.example.service_interface.AddressService;

@Controller
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // LIST
    @GetMapping
    public String listAddresses(Model model) {

        model.addAttribute("addresses", addressService.getAllAddresses());

        return "addresses/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String addressDetail(
            @PathVariable Integer id,
            Model model) {

        Address address = addressService.getAddressById(id);

        model.addAttribute(
                "address",
                address);

        return "addresses/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute(
                "address",
                new Address());

        return "addresses/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createAddress(
            @ModelAttribute Address address) {

        addressService.saveAddress(address);

        return "redirect:/addresses";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        Address address = addressService.getAddressById(id);

        model.addAttribute(
                "address",
                address);

        return "addresses/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editAddress(
            @PathVariable Integer id,
            @ModelAttribute Address address) {

        addressService.editAddress(id, address);

        return "redirect:/addresses";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteAddress(
            @PathVariable Integer id) {

        addressService.deleteAddressById(id);

        return "redirect:/addresses";
    }
}
