package com.example.service_interface;

import com.example.dto.InvoiceItemDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface InvoiceItemService {

    InvoiceItemDTO getInvoiceItemById(Integer id, MyUserDetail userDetail);

    List<InvoiceItemDTO> getAllInvoiceItems(MyUserDetail userDetail);

    InvoiceItemDTO saveInvoiceItem(InvoiceItemDTO invoiceItemDTO, MyUserDetail userDetail);

    InvoiceItemDTO editInvoiceItem(Integer id, InvoiceItemDTO invoiceItemDTO, MyUserDetail userDetail);

    void deleteInvoiceItemById(Integer id, MyUserDetail userDetail);
}
