package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
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
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id: " + id));
    }

    @Override
    public List<Discount> getDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public Discount saveDiscount(Discount discount) {
        if (discount == null) {
            throw new BadRequestException("Discount must not be null");
        }

        return discountRepository.save(discount);
    }

    // Soft deletion
    @Override
    public void deleteDiscountById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        discountRepository.findById(id).ifPresent(discount -> {
            if (discount.getDeleted())
                return;

            discountRepository.softDeleteById(id);
        });
    }

    @Override
    public Discount editDiscount(Integer id, Discount discount) {
        if (id == null || discount == null) {
            throw new BadRequestException("ID or discount to edit must not be null");
        }

        return discountRepository.findById(id).map(existingDiscount -> {
            existingDiscount.setDiscountName(discount.getDiscountName());
            existingDiscount.setDiscountCode(discount.getDiscountCode());
            existingDiscount.setDiscountType(discount.getDiscountType());
            existingDiscount.setDiscountValue(discount.getDiscountValue());
            existingDiscount.setDiscountDescription(discount.getDiscountDescription());
            existingDiscount.setValidFrom(discount.getValidFrom());
            existingDiscount.setValidTo(discount.getValidTo());

            return discountRepository.save(existingDiscount);
        }).orElse(null);
    }
}
