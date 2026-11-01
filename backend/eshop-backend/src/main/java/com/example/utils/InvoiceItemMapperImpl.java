package com.example.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.dto.InvoiceDTO;
import com.example.dto.InvoiceItemDTO;
import com.example.exception.BadRequestException;
import com.example.model.Invoice;
import com.example.model.InvoiceItem;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class InvoiceItemMapperImpl implements ObjectMapper<InvoiceItem, InvoiceItemDTO> {
    private final ObjectMapper<Invoice, InvoiceDTO> invoiceMapper;

    public InvoiceItemMapperImpl(ObjectMapper<Invoice, InvoiceDTO> invoiceMapper) {
        this.invoiceMapper = invoiceMapper;
    }

    @Override
    @NonNull
    public InvoiceItem mapToEntity(InvoiceItemDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if (objectDTO.getProductId() == null || objectDTO.getLineTotal() == null ||
                objectDTO.getQuantity() == null || objectDTO.getUnitPrice() == null) {
            throw new BadRequestException(
                    "Product id, quantity, line total and unit price are required");
        }

        InvoiceItem invoiceItem = new InvoiceItem();

        invoiceItem.setProductId(objectDTO.getProductId());
        invoiceItem.setLineTotal(objectDTO.getLineTotal());
        invoiceItem.setQuantity(objectDTO.getQuantity());
        invoiceItem.setNotes(objectDTO.getNotes());
        invoiceItem.setUnitPrice(objectDTO.getUnitPrice());
        invoiceItem.setInvoice(invoiceMapper.mapToEntity(objectDTO.getInvoiceDTO()));

        return invoiceItem;
    }

    @Override
    public InvoiceItemDTO mapToDTO(InvoiceItem objectEntity) {
        InvoiceItemDTO invoiceItemDTO = new InvoiceItemDTO();

        invoiceItemDTO.setInvoiceItemId(objectEntity.getInvoiceItemId());
        invoiceItemDTO.setProductId(objectEntity.getProductId());
        invoiceItemDTO.setLineTotal(objectEntity.getLineTotal());
        invoiceItemDTO.setQuantity(objectEntity.getQuantity());
        invoiceItemDTO.setNotes(objectEntity.getNotes());
        invoiceItemDTO.setUnitPrice(objectEntity.getUnitPrice());
        invoiceItemDTO.setInvoiceDTO(invoiceMapper.mapToDTO(objectEntity.getInvoice()));

        return invoiceItemDTO;
    }
}
