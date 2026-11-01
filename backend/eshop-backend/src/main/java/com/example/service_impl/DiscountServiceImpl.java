package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.DiscountDTO;
import com.example.exception.BadRequestException;
import com.example.model.Discount;
import com.example.model.MyUserDetail;
import com.example.repository.DiscountRepository;
import com.example.service_interface.DiscountService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;

    private final ObjectMapper<Discount, DiscountDTO> discountMapper;

    public DiscountServiceImpl(DiscountRepository discountRepository,
            ObjectMapper<Discount, DiscountDTO> discountMapper) {
        this.discountRepository = discountRepository;
        this.discountMapper = discountMapper;
    }

    @Override
    public DiscountDTO getDiscountById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return discountRepository.findById(id).map(discount -> discountMapper.mapToDTO(discount))
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id: " + id));
    }

    @Override
    public List<DiscountDTO> getDiscounts(MyUserDetail userDetail) {
        return discountRepository.findAll().stream()
                .map(discountMapper::mapToDTO).toList();
    }

    @Override
    public DiscountDTO saveDiscount(DiscountDTO discountDTO, MyUserDetail userDetail) {
        if (discountDTO == null) {
            throw new BadRequestException("Discount must not be null");
        }

        discountDTO.setCreatedAt(Instant.now());
        discountDTO.setUpdatedAt(Instant.now());

        discountDTO.setCreatedBy(userDetail.getUserId());
        discountDTO.setUpdatedBy(userDetail.getUserId());

        Discount savedDiscount = discountRepository.save(discountMapper.mapToEntity(discountDTO));

        return discountMapper.mapToDTO(savedDiscount);
    }

    // Soft deletion
    @Override
    public void deleteDiscountById(Integer id, MyUserDetail userDetail) {
        Discount discountToDelete = getDiscountEntityById(id);

        if (discountToDelete.isDeleted())
            return;

        discountToDelete.setUpdatedAt(Instant.now());
        discountToDelete.setUpdatedBy(userDetail.getUserId());

        discountRepository.save(discountToDelete);
        // discountRepository.softDeleteById(existingDiscount.getDiscountId());
    }

    @Override
    public DiscountDTO editDiscount(Integer id, DiscountDTO discountDTO, MyUserDetail userDetail) {
        if (discountDTO == null) {
            throw new BadRequestException("Discount to edit must not be null");
        }

        Discount discountToEdit = getDiscountEntityById(id);

        discountToEdit.setDiscountName(discountDTO.getDiscountName());
        discountToEdit.setDiscountCode(discountDTO.getDiscountCode());
        discountToEdit.setDiscountType(discountDTO.getDiscountType());
        discountToEdit.setDiscountValue(discountDTO.getDiscountValue());
        discountToEdit.setDiscountDescription(discountDTO.getDiscountDescription());
        discountToEdit.setValidFrom(discountDTO.getValidFrom());
        discountToEdit.setValidTo(discountDTO.getValidTo());
        discountToEdit.setUpdatedAt(Instant.now());
        discountToEdit.setUpdatedBy(userDetail.getUserId());

        return discountMapper.mapToDTO(discountRepository.save(discountToEdit));
    }

    private Discount getDiscountEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return discountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Discount not found with id: " + id));
    }
}
