package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.InvoiceDTO;
import com.example.exception.BadRequestException;
import com.example.model.Invoice;
import com.example.model.MyUserDetail;
import com.example.repository.InvoiceRepository;
import com.example.service_interface.InvoiceService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;

    private final ObjectMapper<Invoice, InvoiceDTO> invoiceMapper;

    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, ObjectMapper<Invoice, InvoiceDTO> invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    public InvoiceDTO getInvoiceById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return invoiceRepository.findById(id).map(invoice -> invoiceMapper.mapToDTO(invoice))
                .orElseThrow(() -> new ResourceNotFoundException("Invoice not found with id: " + id));
    }

    @Override
    public List<InvoiceDTO> getInvoices(MyUserDetail userDetail) {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::mapToDTO).toList();
    }
}
