package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Invoice;
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
    public List<Invoice> listInvoices() {
        return invoiceService.getInvoices();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Invoice orderDetail(@PathVariable Integer id) {
        return invoiceService.getInvoiceById(id);
    }
}
