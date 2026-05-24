package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.Discount;
import com.example.repository.DiscountRepository;
import com.example.service_interface.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public Discount getDiscountById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDiscountById'");
    }

    @Override
    public List<Discount> getAllDiscounts() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllDiscounts'");
    }

    @Override
    public void saveDiscount(Discount discount) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveDiscount'");
    }

    @Override
    public void deleteDiscountById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteDiscountById'");
    }

}
