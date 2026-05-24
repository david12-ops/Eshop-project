package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.model.Invoice;
import com.example.repository.InvoiceRepository;
import com.example.service_interface.InvoiceService;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public List<Invoice> getAllInvoices() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAllInvoices'");
    }

    @Override
    public void saveInvoice(Invoice invoice) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveInvoice'");
    }

    @Override
    public void deleteInvoiceById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteInvoiceById'");
    }

    @Override
    @Transactional
    public Invoice getInvoiceWithItems(Integer id) {

        return invoiceRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
    }
}
