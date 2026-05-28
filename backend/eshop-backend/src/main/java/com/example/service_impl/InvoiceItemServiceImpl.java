package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
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
        return invoiceItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice item not found with id: " + id));
    }

    @Override
    public List<InvoiceItem> getAllInvoiceItems() {
        return invoiceItemRepository.findAll();
    }

    @Override
    public void saveInvoiceItem(InvoiceItem invoiceItem) {
        if (invoiceItem == null)
            return;

        invoiceItemRepository.save(invoiceItem);
    }

    @Override
    public void deleteInvoiceItemById(Integer id) {
        if (id == null)
            return;

        // check
        invoiceItemRepository.findById(id).ifPresent(invoiceItem -> invoiceItemRepository.deleteById(id));
    }

    @Override
    public void editInvoiceItem(Integer id, InvoiceItem invoiceItem) {
        if (id == null || invoiceItem == null)
            return;

        invoiceItemRepository.findById(id).ifPresent(existingInvoiceItem -> {

            existingInvoiceItem.setLineTotal(invoiceItem.getLineTotal());
            existingInvoiceItem.setQuantity(invoiceItem.getQuantity());
            existingInvoiceItem.setNotes(invoiceItem.getNotes());
            existingInvoiceItem.setUnitPrice(invoiceItem.getUnitPrice());

            invoiceItemRepository.save(existingInvoiceItem);
        });
    }
}
