package com.example.service_interface;

import java.util.List;

import com.example.model.Invoice;

public interface InvoiceService {

    Invoice getInvoiceWithItems(Integer id);

    List<Invoice> getAllInvoices();

    void saveInvoice(Invoice invoice);

    void deleteInvoiceById(Integer id);

}
