package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.InvoiceDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.InvoiceService;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // LIST
    @GetMapping
    public List<InvoiceDTO> listInvoices(@AuthenticationPrincipal MyUserDetail userDetail) {
        return invoiceService.getInvoices(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public InvoiceDTO orderDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return invoiceService.getInvoiceById(id, userDetail);
    }
}
