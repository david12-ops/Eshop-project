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
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {
    private final InvoiceItemRepository invoiceItemRepository;

    private final ObjectMapper<InvoiceItem, InvoiceItemDTO> invoiceItemMapper;

    public InvoiceItemServiceImpl(InvoiceItemRepository invoiceItemRepository,
            ObjectMapper<InvoiceItem, InvoiceItemDTO> invoiceItemMapper) {
        this.invoiceItemRepository = invoiceItemRepository;
        this.invoiceItemMapper = invoiceItemMapper;
    }

    @Override
    public InvoiceItemDTO getInvoiceItemById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return invoiceItemRepository.findById(id).map(invoiceItem -> invoiceItemMapper.mapToDTO(invoiceItem))
                .orElseThrow(() -> new ResourceNotFoundException("Invoice item not found with id: " + id));
    }

    @Override
    public List<InvoiceItemDTO> getAllInvoiceItems(MyUserDetail userDetail) {
        return invoiceItemRepository.findAll().stream()
                .map(invoiceItemMapper::mapToDTO).toList();
    }

    @Override
    public InvoiceItemDTO saveInvoiceItem(InvoiceItemDTO invoiceItemDTO, MyUserDetail userDetail) {
        if (invoiceItemDTO == null) {
            throw new BadRequestException("Invoice item must not be null");
        }

        InvoiceItem savedInvoiceItem = invoiceItemRepository
                .save(invoiceItemMapper.mapToEntity(invoiceItemDTO));

        return invoiceItemMapper.mapToDTO(invoiceItemRepository.save(savedInvoiceItem));
    }

    // Soft deletion
    // TODO Deletion update
    @Override
    public void deleteInvoiceItemById(Integer id, MyUserDetail userDetail) {
        InvoiceItem invoiceItemToDelete = getInvoiceItemEmtityById(id);

        invoiceItemRepository.softDeleteById(invoiceItemToDelete.getInvoiceItemId());
    }

    @Override
    public InvoiceItemDTO editInvoiceItem(Integer id, InvoiceItemDTO invoiceItemDTO, MyUserDetail userDetail) {
        if (invoiceItemDTO == null) {
            throw new BadRequestException("Invoice item to edit must not be null");
        }

        InvoiceItem invoiceItemToUpdate = getInvoiceItemEmtityById(id);

        invoiceItemToUpdate.setLineTotal(invoiceItemDTO.getLineTotal());
        invoiceItemToUpdate.setQuantity(invoiceItemDTO.getQuantity());
        invoiceItemToUpdate.setNotes(invoiceItemDTO.getNotes());
        invoiceItemToUpdate.setUnitPrice(invoiceItemDTO.getUnitPrice());

        return invoiceItemMapper.mapToDTO(invoiceItemRepository.save(invoiceItemToUpdate));
    }

    private InvoiceItem getInvoiceItemEmtityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return invoiceItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Invoice item not found with id: " + id));
    }
}
