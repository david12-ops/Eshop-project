package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
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
        return discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id: " + id));
    }

    @Override
    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    @Override
    public void saveDiscount(Discount discount) {
        if (discount == null)
            return;

        discountRepository.save(discount);
    }

    @Override
    public void deleteDiscountById(Integer id) {
        if (id == null)
            return;

        discountRepository.findById(id).ifPresent(discount -> discountRepository.deleteById(id));
    }

    @Override
    public void editDiscount(Integer id, Discount discount) {
        if (id == null || discount == null)
            return;

        discountRepository.findById(id).ifPresent(existingDiscount -> {

            existingDiscount.setDiscountName(discount.getDiscountName());
            existingDiscount.setDiscountCode(discount.getDiscountCode());
            existingDiscount.setDiscountType(discount.getDiscountType());
            existingDiscount.setDiscountValue(discount.getDiscountValue());
            existingDiscount.setDiscountDescription(discount.getDiscountDescription());
            existingDiscount.setValidFrom(discount.getValidFrom());
            existingDiscount.setValidTo(discount.getValidTo());

            discountRepository.save(existingDiscount);
        });
    }
}
