package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.DiscountDTO;
import com.example.exception.BadRequestException;
import com.example.model.Discount;
import com.example.model.MyUserDetail;
import com.example.repository.DiscountRepository;
import com.example.service_interface.DiscountService;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    @Override
    public DiscountDTO getDiscountById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id: " + id));
    }

    @Override
    public List<DiscountDTO> getDiscounts(MyUserDetail userDetail) {
        return discountRepository.findAll();
    }

    @Override
    public DiscountDTO saveDiscount(DiscountDTO discountDTO, MyUserDetail userDetail) {
        if (discountDTO == null) {
            throw new BadRequestException("Discount must not be null");
        }

        return discountRepository.save(discount);
    }

    // Soft deletion
    @Override
    public void deleteDiscountById(Integer id, MyUserDetail userDetail) {
        Discount existingDiscount = getDiscountById(id);

        if (existingDiscount.isDeleted())
            return;

        discountRepository.softDeleteById(existingDiscount.getDiscountId());
    }

    @Override
    public DiscountDTO editDiscount(Integer id, DiscountDTO discountDTO, MyUserDetail userDetail) {
        if (discountDTO == null) {
            throw new BadRequestException("Discount to edit must not be null");
        }

        Discount existingDiscount = getDiscountById(id);

        existingDiscount.setDiscountName(discount.getDiscountName());
        existingDiscount.setDiscountCode(discount.getDiscountCode());
        existingDiscount.setDiscountType(discount.getDiscountType());
        existingDiscount.setDiscountValue(discount.getDiscountValue());
        existingDiscount.setDiscountDescription(discount.getDiscountDescription());
        existingDiscount.setValidFrom(discount.getValidFrom());
        existingDiscount.setValidTo(discount.getValidTo());

        return discountRepository.save(existingDiscount);
    }
}
