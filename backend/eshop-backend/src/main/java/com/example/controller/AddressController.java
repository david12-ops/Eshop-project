package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Address;
import com.example.service_interface.AddressService;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // LIST
    @GetMapping
    public List<Address> listAddresses() {
        return addressService.getAddresses();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Address addressDetail(@PathVariable Integer id) {
        return addressService.getAddressById(id);
    }

    // CREATE
    @PostMapping 
    public Address createAddress(@RequestBody Address address) {
        return addressService.saveAddress(address);
    }

    // EDIT 
    @PutMapping ("/{id}")
    public Address editAddress(
            @PathVariable Integer id,
            @RequestBody Address address) {
        return addressService.editAddress(id, address);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddressById(id);
    }
}
