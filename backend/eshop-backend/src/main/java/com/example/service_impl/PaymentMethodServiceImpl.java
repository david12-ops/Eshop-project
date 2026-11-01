package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.PaymentMethodDTO;
import com.example.exception.BadRequestException;
import com.example.model.MyUserDetail;
import com.example.model.PaymentMethod;
import com.example.repository.PaymentMethodRepository;
import com.example.service_interface.PaymentMethodService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class PaymentMethodServiceImpl implements PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    private final ObjectMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper;

    public PaymentMethodServiceImpl(PaymentMethodRepository paymentMethodRepository,
            ObjectMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper) {
        this.paymentMethodRepository = paymentMethodRepository;
        this.paymentMethodMapper = paymentMethodMapper;
    }

    @Override
    public PaymentMethodDTO getPaymentMethodById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return paymentMethodRepository.findById(id).map(paymentMethod -> paymentMethodMapper.mapToDTO(paymentMethod))
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + id));
    }

    @Override
    public List<PaymentMethodDTO> getPaymentMethods(MyUserDetail userDetail) {
        return paymentMethodRepository.findAll().stream()
                .map(paymentMethodMapper::mapToDTO).toList();
    }

    @Override
    public PaymentMethodDTO savePaymentMethod(PaymentMethodDTO paymentMethodDTO, MyUserDetail userDetail) {
        if (paymentMethodDTO == null) {
            throw new BadRequestException("Payment method must not be null");
        }

        paymentMethodDTO.setCreatedAt(Instant.now());
        paymentMethodDTO.setUpdatedAt(Instant.now());

        paymentMethodDTO.setCreatedBy(userDetail.getUserId());
        paymentMethodDTO.setUpdatedBy(userDetail.getUserId());

        paymentMethodDTO.setDeleted(false);
        paymentMethodDTO.setDeletedAt(null);

        PaymentMethod savedPaymentMethod = paymentMethodRepository
                .save(paymentMethodMapper.mapToEntity(paymentMethodDTO));

        return paymentMethodMapper.mapToDTO(savedPaymentMethod);
    }

    // Soft deletion
    @Override
    public void deletePaymentMethodById(Integer id, MyUserDetail userDetail) {
        PaymentMethod paymentMethodToDelete = getPaymentMethodEntityById(id);

        if (paymentMethodToDelete.isDeleted())
            return;

        paymentMethodToDelete.setUpdatedAt(Instant.now());
        paymentMethodToDelete.setUpdatedBy(userDetail.getUserId());

        paymentMethodToDelete.setDeleted(true);
        paymentMethodToDelete.setDeletedAt(Instant.now());

        paymentMethodRepository.save(paymentMethodToDelete);
        // paymentMethodRepository.softDeleteById(existingPaymentMethod.getPaymentMethodId());
    }

    @Override
    public PaymentMethodDTO editPaymentMethod(Integer id, PaymentMethodDTO paymentMethodDTO, MyUserDetail userDetail) {
        if (paymentMethodDTO == null) {
            throw new BadRequestException("Payment method to edit must not be null");
        }

        PaymentMethod paymentMethodToUpdate = getPaymentMethodEntityById(id);

        paymentMethodToUpdate.setMethodName(paymentMethodDTO.getMethodName());
        paymentMethodToUpdate.setMethodType(paymentMethodDTO.getMethodType());
        paymentMethodToUpdate.setMethodDescription(paymentMethodDTO.getMethodDescription());
        paymentMethodToUpdate.setUpdatedAt(Instant.now());
        paymentMethodToUpdate.setUpdatedBy(userDetail.getUserId());
        paymentMethodToUpdate.setDeleted(false);
        paymentMethodToUpdate.setDeletedAt(null);

        return paymentMethodMapper.mapToDTO(paymentMethodRepository.save(paymentMethodToUpdate));
    }

    private PaymentMethod getPaymentMethodEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return paymentMethodRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment method not found with id: " + id));
    }
}
