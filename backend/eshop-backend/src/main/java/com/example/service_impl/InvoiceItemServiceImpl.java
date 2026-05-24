package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.InvoiceItem;
import com.example.repository.InvoiceItemRepository;
import com.example.service_interface.InvoiceItemService;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    public InvoiceItem getInvoiceItemById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getInvoiceItemById'");
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllInvoiceItems'");
    }

    @Override
    public void saveInvoiceItem(InvoiceItem invoiceItem) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveInvoiceItem'");
    }

    @Override
    public void deleteInvoiceItemById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteInvoiceItemById'");
    }

}
