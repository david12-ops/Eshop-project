package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.CurrencyDTO;
import com.example.exception.BadRequestException;
import com.example.model.Currency;
import com.example.model.MyUserDetail;
import com.example.repository.CurrencyRepository;
import com.example.service_interface.CurrencyService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    private final ObjectMapper<Currency, CurrencyDTO> currencyMapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository,
            ObjectMapper<Currency, CurrencyDTO> currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public CurrencyDTO getCurrencyByCode(String code, MyUserDetail userDetail) {
        if (code == null) {
            throw new BadRequestException("Code must not be null");
        }

        return currencyRepository.findById(code).map(currency -> currencyMapper.mapToDTO(currency))
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with code: " + code));
    }

    @Override
    public List<CurrencyDTO> getCurrencies(MyUserDetail userDetail) {
        return currencyRepository.findAll().stream()
                .map(currencyMapper::mapToDTO).toList();
    }

    @Override
    public CurrencyDTO saveCurrency(CurrencyDTO currencyDTO, MyUserDetail userDetail) {
        if (currencyDTO == null) {
            throw new BadRequestException("Currency must not be null");
        }

        currencyDTO.setCreatedAt(Instant.now());
        currencyDTO.setUpdatedAt(Instant.now());

        currencyDTO.setCreatedBy(userDetail.getUserId());
        currencyDTO.setUpdatedBy(userDetail.getUserId());

        currencyDTO.setDeleted(false);
        currencyDTO.setDeletedAt(null);

        Currency savedCurrency = currencyRepository.save(currencyMapper.mapToEntity(currencyDTO));

        return currencyMapper.mapToDTO(savedCurrency);
    }

    // Soft deletion
    @Override
    public void deleteCurrencyById(String code, MyUserDetail userDetail) {
        Currency currencyToDelete = getCurrencyEntityByCode(code);

        if (currencyToDelete.isDeleted())
            return;

        currencyToDelete.setUpdatedAt(Instant.now());
        currencyToDelete.setUpdatedBy(userDetail.getUserId());

        currencyToDelete.setDeleted(true);
        currencyToDelete.setDeletedAt(Instant.now());

        currencyRepository.save(currencyToDelete);
        // currencyRepository.softDeleteById(existingCurrency.getCurrencyCode());
    }

    @Override
    public CurrencyDTO editCurrency(String code, CurrencyDTO currencyDTO, MyUserDetail userDetail) {
        if (currencyDTO == null) {
            throw new BadRequestException("Currency to edit must not be null");
        }

        Currency currencyToUpdate = getCurrencyEntityByCode(code);

        currencyToUpdate.setCurrencyName(currencyDTO.getCurrencyName());
        currencyToUpdate.setSymbol(currencyDTO.getSymbol());
        currencyToUpdate.setUpdatedAt(Instant.now());
        currencyToUpdate.setUpdatedBy(userDetail.getUserId());
        currencyToUpdate.setDeleted(false);
        currencyToUpdate.setDeletedAt(null);

        return currencyMapper.mapToDTO(currencyRepository.save(currencyToUpdate));
    }

    private Currency getCurrencyEntityByCode(String code) {
        if (code == null) {
            throw new BadRequestException("Code must not be null");
        }

        return currencyRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Code not found with code: " + code));
    }
}
