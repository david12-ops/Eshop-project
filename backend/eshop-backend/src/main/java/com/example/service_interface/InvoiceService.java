package com.example.service_interface;

import java.util.List;

import com.example.model.Invoice;

public interface InvoiceService {

    Invoice getInvoiceById(Integer id);

    List<Invoice> getAllInvoices();
}
