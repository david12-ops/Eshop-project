package com.example.service_interface;

import com.example.dto.DiscountDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface DiscountService {

    DiscountDTO getDiscountById(Integer id, MyUserDetail userDetail);

    List<DiscountDTO> getDiscounts(MyUserDetail userDetail);

    DiscountDTO saveDiscount(DiscountDTO discountDTO, MyUserDetail userDetail);

    DiscountDTO editDiscount(Integer id, DiscountDTO discountDTO, MyUserDetail userDetail);

    void deleteDiscountById(Integer id, MyUserDetail userDetail);
}
