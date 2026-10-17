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

import com.example.dto.AddressDTO;
import com.example.model.MyUserDetail;
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
    public List<AddressDTO> listAddresses(@AuthenticationPrincipal MyUserDetail userDetail) {
        return addressService.getAddresses(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public AddressDTO addressDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return addressService.getAddressById(id, userDetail);
    }

    // CREATE
    @PostMapping
    public AddressDTO createAddress(
            @RequestBody AddressDTO addressDTO,
            @AuthenticationPrincipal MyUserDetail userDetails) {
        return addressService.saveAddress(addressDTO, userDetails);
    }

    // EDIT
    @PutMapping("/{id}")
    public AddressDTO editAddress(
            @PathVariable Integer id,
            @RequestBody AddressDTO addressDTO,
            @AuthenticationPrincipal MyUserDetail userDetails) {
        return addressService.editAddress(id, addressDTO, userDetails);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteAddress(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetails) {
        addressService.deleteAddressById(id, userDetails);
    }
}
