package com.example.service_interface;

import com.example.dto.InvoiceDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface InvoiceService {

    InvoiceDTO getInvoiceById(Integer id, MyUserDetail userDetail);

    List<InvoiceDTO> getInvoices(MyUserDetail userDetail);
}
