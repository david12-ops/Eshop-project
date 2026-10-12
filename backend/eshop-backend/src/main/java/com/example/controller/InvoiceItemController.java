package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.InvoiceItem;
import com.example.service_interface.InvoiceItemService;

@RestController 
public class InvoiceItemController {
    private final InvoiceItemService invoiceItemService;

    public InvoiceItemController(InvoiceItemService invoiceItemService){
        this.invoiceItemService = invoiceItemService;
    }

    // LIST
    @GetMapping 
    public List<InvoiceItem> listInvoiceItems(){
        return invoiceItemService.getAllInvoiceItems();
    }

    // DETAIL
    @GetMapping("/{id}")
    public InvoiceItem invoiceItemDetail(@PathVariable Integer id){
        return invoiceItemService.getInvoiceItemById(id);
    }

    // CREATE
    @PostMapping("/create")
    public InvoiceItem createInvoiceItem(@RequestBody InvoiceItem invoiceItem){
        return invoiceItemService.saveInvoiceItem(invoiceItem);
    }

    // UPDATE
    @PutMapping 
    public InvoiceItem editInvoiceItem(
        @PathVariable Integer id,
        @RequestBody InvoiceItem invoiceItem){
        return  invoiceItemService.editInvoiceItem(id, invoiceItem);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteInvoiceItem(@PathVariable Integer id){
        invoiceItemService.deleteInvoiceItemById(id);
    }
}
