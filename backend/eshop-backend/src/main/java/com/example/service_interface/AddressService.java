package com.example.service_interface;

import com.example.dto.AddressDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface AddressService {

    List<AddressDTO> getAddresses(MyUserDetail userDetail);

    AddressDTO getAddressById(Integer id, MyUserDetail userDetail);

    AddressDTO saveAddress(AddressDTO addressDTO, MyUserDetail userDetail);

    AddressDTO editAddress(Integer id, AddressDTO addressDTO, MyUserDetail userDetail);

    void deleteAddressById(Integer id, MyUserDetail userDetail);
}