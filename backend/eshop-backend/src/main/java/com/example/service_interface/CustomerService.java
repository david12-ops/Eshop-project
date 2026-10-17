package com.example.service_interface;

import com.example.dto.CustomerDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface CustomerService {

    CustomerDTO getCustomerById(Integer id, MyUserDetail userDetail);

    List<CustomerDTO> getCustomers(MyUserDetail userDetail);

    CustomerDTO saveCustomer(CustomerDTO customerDTO, MyUserDetail userDetail);

    CustomerDTO editCustomer(Integer id, CustomerDTO customerDTO, MyUserDetail userDetail);

    void deleteCustomerById(Integer id, MyUserDetail userDetail);
}
