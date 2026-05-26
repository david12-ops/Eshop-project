package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Invoice;
import com.example.service_interface.InvoiceService;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    // LIST
    @GetMapping
    public String listInvoices(Model model) {

        model.addAttribute("invoices", invoiceService.getAllInvoices());

        return "invoices/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String orderDetail(
            @PathVariable Integer id,
            Model model) {

        Invoice invoice = invoiceService.getInvoiceById(id);

        model.addAttribute(
                "invoice",
                invoice);

        return "invoices/detail";
    }
}
