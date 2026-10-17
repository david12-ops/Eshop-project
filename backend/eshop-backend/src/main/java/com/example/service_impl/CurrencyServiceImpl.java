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
import com.example.utils.CurrencyMapper;

@Service
public class CurrencyServiceImpl implements CurrencyService {
    private final CurrencyRepository currencyRepository;

    private final CurrencyMapper currencyMapper;

    public CurrencyServiceImpl(CurrencyRepository currencyRepository, CurrencyMapper currencyMapper) {
        this.currencyRepository = currencyRepository;
        this.currencyMapper = currencyMapper;
    }

    @Override
    public CurrencyDTO getCurrencyByCode(String code, MyUserDetail userDetail) {
        if (code == null) {
            throw new BadRequestException("Code must not be null");
        }

        return currencyRepository.findById(code).map(currency -> currencyMapper.mapToCurrencyDTO(currency))
                .orElseThrow(() -> new ResourceNotFoundException("Currency not found with code: " + code));
    }

    @Override
    public List<CurrencyDTO> getCurrencies(MyUserDetail userDetail) {
        return currencyRepository.findAll().stream().map(currency -> currencyMapper.mapToCurrencyDTO(currency))
                .toList();
    }

    @Override
    public CurrencyDTO saveCurrency(CurrencyDTO currencyDTO, MyUserDetail userDetail) {
        if (currencyDTO == null) {
            throw new BadRequestException("Currency must not be null");
        }

        currencyDTO.setDeleted(false);
        currencyDTO.setDeletedAt(null);

        if (currencyDTO.getCreatedAt() == null) {
            currencyDTO.setCreatedAt(Instant.now());
        }

        if (currencyDTO.getUpdatedAt() == null) {
            currencyDTO.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (currencyDTO.getCreatedBy() == null) {
                currencyDTO.setCreatedBy(userDetail.getUserId());
            }

            if (currencyDTO.getUpdatedBy() == null) {
                currencyDTO.setUpdatedBy(userDetail.getUserId());
            }
        }

        Currency newCurrency = currencyMapper.mapToCurrencyEntity(currencyDTO);

        Currency savedCurrency = currencyRepository.save(newCurrency);

        return currencyMapper.mapToCurrencyDTO(savedCurrency);
    }

    // Soft deletion
    @Override
    public void deleteCurrencyById(String code, MyUserDetail userDetail) {
        Currency existingCurrency = getCurrencyEntityByCode(code);

        if (existingCurrency.getDeleted())
            return;

        currencyRepository.softDeleteById(existingCurrency.getCurrencyCode());
    }

    @Override
    public CurrencyDTO editCurrency(String code, CurrencyDTO currencyDTO, MyUserDetail userDetail) {
        if (currencyDTO == null) {
            throw new BadRequestException("Currency to edit must not be null");
        }

        Currency currencyToUpdate = getCurrencyEntityByCode(code);

        currencyToUpdate.setCurrencyName(currencyDTO.getCurrencyName());
        currencyToUpdate.setSymbol(currencyDTO.getSymbol());
        currencyToUpdate.setDeleted(false);
        currencyToUpdate.setDeletedAt(null);

        if (currencyDTO.getUpdatedAt() == null) {
            currencyToUpdate.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (currencyDTO.getCreatedBy() == null) {
                currencyToUpdate.setCreatedBy(userDetail.getUserId());
            }

            if (currencyDTO.getUpdatedBy() == null) {
                currencyToUpdate.setUpdatedBy(userDetail.getUserId());
            }
        }

        Currency updatedCurrency = currencyRepository.save(currencyToUpdate);

        return currencyMapper.mapToCurrencyDTO(updatedCurrency);
    }

    private Currency getCurrencyEntityByCode(String code) {
        if (code == null) {
            throw new BadRequestException("Code must not be null");
        }

        return currencyRepository.findById(code)
                .orElseThrow(() -> new ResourceNotFoundException("Code not found with code: " + code));
    }
}
