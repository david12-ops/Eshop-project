package com.example.service_interface;

import java.util.List;

import com.example.model.InvoiceItem;

public interface InvoiceItemService {

    InvoiceItem getInvoiceItemById(Integer id);

    List<InvoiceItem> getAllInvoiceItems();

    void saveInvoiceItem(InvoiceItem invoiceItem);

    void editInvoiceItem(Integer id, InvoiceItem invoiceItem);

    void deleteInvoiceItemById(Integer id);
}
