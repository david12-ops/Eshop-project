package com.example.service_interface;

import com.example.dto.CurrencyDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface CurrencyService {

    CurrencyDTO getCurrencyByCode(String code, MyUserDetail userDetail);

    List<CurrencyDTO> getCurrencies(MyUserDetail userDetail);

    CurrencyDTO saveCurrency(CurrencyDTO currencyDTO, MyUserDetail userDetail);

    CurrencyDTO editCurrency(String code, CurrencyDTO currencyDTO, MyUserDetail userDetail);

    void deleteCurrencyById(String code, MyUserDetail userDetail);
}
