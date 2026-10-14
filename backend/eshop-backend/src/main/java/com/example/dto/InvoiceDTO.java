package com.example.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InvoiceDTO {
    private String invoiceCode;
    private LocalDate issueDate;
    private LocalDate dueDate;
    private LocalDate paymentDate;
    private BigDecimal subtotal;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String notes;
    private Instant createdAt;
    private Instant updatedAt;
    private Set<DiscountDTO> discountsDTO = new HashSet<>();
    private OrderDTO orderDTO;
    private PaymentMethodDTO paymentMethodDTO;
    private AddressDTO billingAddressDTO;
    private CurrencyDTO currencyDTO;
    private List<InvoiceItemDTO> itemsDTO = new ArrayList<>();

    public InvoiceDTO() {
    }

    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public OrderDTO getOrderDTO() {
        return orderDTO;
    }

    public void setOrderDTO(OrderDTO orderDTO) {
        this.orderDTO = orderDTO;
    }

    public PaymentMethodDTO getPaymentMethodDTO() {
        return paymentMethodDTO;
    }

    public void setPaymentMethodDTO(PaymentMethodDTO paymentMethodDTO) {
        this.paymentMethodDTO = paymentMethodDTO;
    }

    public AddressDTO getBillingAddressDTO() {
        return billingAddressDTO;
    }

    public void setBillingAddressDTO(AddressDTO billingAddressDTO) {
        this.billingAddressDTO = billingAddressDTO;
    }

    public CurrencyDTO getCurrencyDTO() {
        return currencyDTO;
    }

    public void setCurrencyDTO(CurrencyDTO currencyDTO) {
        this.currencyDTO = currencyDTO;
    }

    public List<InvoiceItemDTO> getItemsDTO() {
        return itemsDTO;
    }

    public void setItemsDTO(List<InvoiceItemDTO> itemsDTO) {
        this.itemsDTO = itemsDTO;
    }

    public Set<DiscountDTO> getDiscountsDTO() {
        return discountsDTO;
    }

    public void setDiscountsDTO(Set<DiscountDTO> discountsDTO) {
        this.discountsDTO = discountsDTO;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                ", invoiceCode='" + invoiceCode + '\'' +
                ", issueDate=" + issueDate +
                ", dueDate=" + dueDate +
                ", paymentDate=" + paymentDate +
                ", subtotal=" + subtotal +
                ", taxAmount=" + taxAmount +
                ", totalAmount=" + totalAmount +
                ", notes='" + notes + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}