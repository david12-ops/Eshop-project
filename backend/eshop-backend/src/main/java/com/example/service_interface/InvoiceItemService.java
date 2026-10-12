package com.example.service_interface;

import java.util.List;

import com.example.model.InvoiceItem;

public interface InvoiceItemService {

    InvoiceItem getInvoiceItemById(Integer id);

    List<InvoiceItem> getAllInvoiceItems();

    InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem);

    InvoiceItem editInvoiceItem(Integer id, InvoiceItem invoiceItem);

    void deleteInvoiceItemById(Integer id);
}
