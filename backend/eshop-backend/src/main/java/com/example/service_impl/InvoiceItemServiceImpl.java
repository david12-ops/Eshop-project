package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.InvoiceItemDTO;
import com.example.exception.BadRequestException;
import com.example.model.InvoiceItem;
import com.example.model.MyUserDetail;
import com.example.repository.InvoiceItemRepository;
import com.example.service_interface.InvoiceItemService;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {
    private final InvoiceItemRepository invoiceItemRepository;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository) {
        this.invoiceItemRepository = invoiceItemRepository;
    }

    @Override
    public InvoiceItemDTO getInvoiceItemById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return invoiceItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice item not found with id: " + id));
    }

    @Override
    public List<InvoiceItemDTO> getAllInvoiceItems(MyUserDetail userDetail) {
        return invoiceItemRepository.findAll();
    }

    @Override
    public InvoiceItemDTO saveInvoiceItem(InvoiceItemDTO invoiceItemDTO, MyUserDetail userDetail) {
        if (invoiceItemDTO == null) {
            throw new BadRequestException("Invoice item must not be null");
        }

        return invoiceItemRepository.save(invoiceItem);
    }

    @Override
    public void deleteInvoiceItemById(Integer id, MyUserDetail userDetail) {
        InvoiceItem existingInvoiceItem = getInvoiceItemById(id);

        invoiceItemRepository.softDeleteById(existingInvoiceItem.getInvoiceItemId());
    }

    @Override
    public InvoiceItemDTO editInvoiceItem(Integer id, InvoiceItemDTO invoiceItemDTO, MyUserDetail userDetail) {
        if (invoiceItemDTO == null) {
            throw new BadRequestException("Invoice item to edit must not be null");
        }

        InvoiceItem existingInvoiceItem = getInvoiceItemById(id);

        existingInvoiceItem.setLineTotal(invoiceItem.getLineTotal());
        existingInvoiceItem.setQuantity(invoiceItem.getQuantity());
        existingInvoiceItem.setNotes(invoiceItem.getNotes());
        existingInvoiceItem.setUnitPrice(invoiceItem.getUnitPrice());

        return invoiceItemRepository.save(existingInvoiceItem);
    }
}
