package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Address;
import com.example.service_interface.AddressService;
import com.example.service_interface.RegionService;

@Controller
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;
    private final RegionService regionService;

    public AddressController(AddressService addressService, RegionService regionService) {
        this.addressService = addressService;
        this.regionService = regionService;
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
        model.addAttribute("regions", regionService.getAllRegions());

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
        model.addAttribute("regions", regionService.getAllRegions());

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
