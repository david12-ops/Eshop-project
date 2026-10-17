package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.InvoiceItemDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.InvoiceItemService;

@RestController
public class InvoiceItemController {
    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService) {
        this.invoiceItemService = invoiceItemService;
    }

    // LIST
    @GetMapping
    public List<InvoiceItemDTO> listInvoiceItems(@AuthenticationPrincipal MyUserDetail userDetail) {
        return invoiceItemService.getAllInvoiceItems(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public InvoiceItemDTO invoiceItemDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return invoiceItemService.getInvoiceItemById(id, userDetail);
    }
}
