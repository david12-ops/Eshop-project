package com.example.dto;

import java.math.BigDecimal;

public class InvoiceItemDTO {
    private Integer invoiceItemId;
    private Integer productId; // zde
    private BigDecimal lineTotal;
    private Integer quantity;
    private String notes;
    private BigDecimal unitPrice;
    private InvoiceDTO invoiceDTO;

    public InvoiceItemDTO() {
    }

    public Integer getInvoiceItemId() {
        return invoiceItemId;
    }

    public void setInvoiceItemId(Integer invoiceItemId) {
        this.invoiceItemId = invoiceItemId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public InvoiceDTO getInvoice() {
        return invoiceDTO;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoiceDTO = invoice;
    }

    @Override
    public String toString() {
        return "InvoiceItem{" +
                "invoiceItemId=" + invoiceItemId +
                ", productId=" + productId +
                ", lineTotal=" + lineTotal +
                ", quantity=" + quantity +
                ", notes='" + notes + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}