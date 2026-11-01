package com.example.utils;

import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.dto.AddressDTO;
import com.example.dto.CurrencyDTO;
import com.example.dto.DiscountDTO;
import com.example.dto.InvoiceDTO;
import com.example.dto.InvoiceItemDTO;
import com.example.dto.OrderDTO;
import com.example.dto.PaymentMethodDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.Currency;
import com.example.model.Discount;
import com.example.model.Invoice;
import com.example.model.InvoiceItem;
import com.example.model.Order;
import com.example.model.PaymentMethod;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class InvoiceMapperImpl implements ObjectMapper<Invoice, InvoiceDTO> {
    private final ObjectMapper<Order, OrderDTO> orderMapper;
    private final ObjectMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper;
    private final ObjectMapper<Address, AddressDTO> addressMapper;
    private final ObjectMapper<Currency, CurrencyDTO> currencyMapper;
    private final ObjectMapper<InvoiceItem, InvoiceItemDTO> invoiceItemMapper;
    private final ObjectMapper<Discount, DiscountDTO> discountMapper;

    public InvoiceMapperImpl(ObjectMapper<Order, OrderDTO> orderMapper,
            ObjectMapper<PaymentMethod, PaymentMethodDTO> paymentMethodMapper,
            ObjectMapper<Address, AddressDTO> addressMapper,
            ObjectMapper<Currency, CurrencyDTO> currencyMapper,
            ObjectMapper<InvoiceItem, InvoiceItemDTO> invoiceItemMapper,
            ObjectMapper<Discount, DiscountDTO> discountMapper) {
        this.orderMapper = orderMapper;
        this.paymentMethodMapper = paymentMethodMapper;
        this.addressMapper = addressMapper;
        this.currencyMapper = currencyMapper;
        this.invoiceItemMapper = invoiceItemMapper;
        this.discountMapper = discountMapper;
    }

    @Override
    @NonNull
    public Invoice mapToEntity(InvoiceDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if ((objectDTO.getInvoiceCode() == null || objectDTO.getInvoiceCode().isBlank())
                || objectDTO.getIssueDate() == null || objectDTO.getDueDate() == null || objectDTO.getSubtotal() == null
                ||
                objectDTO.getTaxAmount() == null || objectDTO.getTotalAmount() == null
                || objectDTO.getOrderDTO() == null || objectDTO.getPaymentMethodDTO() == null
                || objectDTO.getBillingAddressDTO() == null || objectDTO.getCurrencyDTO() == null) {
            throw new BadRequestException(
                    "Invoice code, issue date, due date, subtotal, tax amount, total amount, order, payment method, billing address and currency are required");
        }

        Invoice invoice = new Invoice();

        invoice.setInvoiceCode(objectDTO.getInvoiceCode());
        invoice.setIssueDate(objectDTO.getIssueDate());
        invoice.setDueDate(objectDTO.getDueDate());
        invoice.setPaymentDate(objectDTO.getPaymentDate());
        invoice.setSubtotal(objectDTO.getSubtotal());
        invoice.setTaxAmount(objectDTO.getTaxAmount());
        invoice.setTotalAmount(objectDTO.getTotalAmount());
        invoice.setNotes(objectDTO.getNotes());
        invoice.setCreatedAt(objectDTO.getCreatedAt());
        invoice.setUpdatedAt(objectDTO.getUpdatedAt());
        invoice.setOrder(orderMapper.mapToEntity(objectDTO.getOrderDTO()));
        invoice.setPaymentMethod(paymentMethodMapper.mapToEntity(objectDTO.getPaymentMethodDTO()));
        invoice.setBillingAddress(addressMapper.mapToEntity(objectDTO.getBillingAddressDTO()));
        invoice.setCurrency(currencyMapper.mapToEntity(objectDTO.getCurrencyDTO()));
        invoice.setItems(objectDTO.getItemsDTO().stream().map(invoiceItemMapper::mapToEntity).toList());
        invoice.setDiscounts(Set.copyOf(objectDTO.getDiscountsDTO().stream()
                .map(discountMapper::mapToEntity).toList()));

        return invoice;
    }

    @Override
    public InvoiceDTO mapToDTO(Invoice objectEntity) {
        InvoiceDTO invoiceDTO = new InvoiceDTO();

        invoiceDTO.setInvoiceId(objectEntity.getInvoiceId());
        invoiceDTO.setInvoiceCode(objectEntity.getInvoiceCode());
        invoiceDTO.setIssueDate(objectEntity.getIssueDate());
        invoiceDTO.setDueDate(objectEntity.getDueDate());
        invoiceDTO.setPaymentDate(objectEntity.getPaymentDate());
        invoiceDTO.setSubtotal(objectEntity.getSubtotal());
        invoiceDTO.setTaxAmount(objectEntity.getTaxAmount());
        invoiceDTO.setTotalAmount(objectEntity.getTotalAmount());
        invoiceDTO.setNotes(objectEntity.getNotes());
        invoiceDTO.setCreatedAt(objectEntity.getCreatedAt());
        invoiceDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        invoiceDTO.setOrderDTO(orderMapper.mapToDTO(objectEntity.getOrder()));
        invoiceDTO.setPaymentMethodDTO(paymentMethodMapper.mapToDTO(objectEntity.getPaymentMethod()));
        invoiceDTO.setBillingAddressDTO(addressMapper.mapToDTO(objectEntity.getBillingAddress()));
        invoiceDTO.setCurrencyDTO(currencyMapper.mapToDTO(objectEntity.getCurrency()));
        invoiceDTO.setItemsDTO(objectEntity.getItems().stream().map(invoiceItemMapper::mapToDTO).toList());
        invoiceDTO.setDiscountsDTO(Set.copyOf(objectEntity.getDiscounts().stream()
                .map(discountMapper::mapToDTO).toList()));

        return invoiceDTO;
    }
}
