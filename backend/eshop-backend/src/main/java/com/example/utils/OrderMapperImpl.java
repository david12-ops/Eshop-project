package com.example.utils;

import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.dto.AddressDTO;
import com.example.dto.CurrencyDTO;
import com.example.dto.CustomerDTO;
import com.example.dto.DiscountDTO;
import com.example.dto.OrderDTO;
import com.example.dto.OrderItemDTO;
import com.example.dto.OrderStatusDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.Currency;
import com.example.model.Customer;
import com.example.model.Discount;
import com.example.model.Order;
import com.example.model.OrderItem;
import com.example.model.OrderStatus;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class OrderMapperImpl implements ObjectMapper<Order, OrderDTO> {
    private final ObjectMapper<Customer, CustomerDTO> customerMapper;
    private final ObjectMapper<OrderStatus, OrderStatusDTO> orderStatusMapper;
    private final ObjectMapper<Currency, CurrencyDTO> currencyMapper;
    private final ObjectMapper<Address, AddressDTO> addressMapper;
    private final ObjectMapper<OrderItem, OrderItemDTO> orderItemMapper;
    private final ObjectMapper<Discount, DiscountDTO> discountMapper;

    public OrderMapperImpl(
            ObjectMapper<Customer, CustomerDTO> customerMapper,
            ObjectMapper<OrderStatus, OrderStatusDTO> orderStatusMapper,
            ObjectMapper<Currency, CurrencyDTO> currencyMapper, ObjectMapper<Address, AddressDTO> addressMapper,
            ObjectMapper<OrderItem, OrderItemDTO> orderItemMapper,
            ObjectMapper<Discount, DiscountDTO> discountMapper) {
        this.customerMapper = customerMapper;
        this.orderStatusMapper = orderStatusMapper;
        this.currencyMapper = currencyMapper;
        this.addressMapper = addressMapper;
        this.orderItemMapper = orderItemMapper;
        this.discountMapper = discountMapper;
    }

    @Override
    @NonNull
    public Order mapToEntity(OrderDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if (objectDTO.getOrderDate() == null || objectDTO.getSubtotal() == null ||
                objectDTO.getTaxAmount() == null || objectDTO.getTotalAmount() == null ||
                objectDTO.getCurrencyDTO() == null || objectDTO.getOrderStatusDTO() == null
                || objectDTO.getCustomerDTO() == null ||
                objectDTO.getDeliveryAddressDTO() == null || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Order date, subtotal, tax amount, total amount, currency, order status, customer, delivery address and deletion state are required");
        }

        Order order = new Order();

        order.setOrderDate(objectDTO.getOrderDate());
        order.setSubtotal(objectDTO.getSubtotal());
        order.setTaxAmount(objectDTO.getTaxAmount());
        order.setTotalAmount(objectDTO.getTotalAmount());
        order.setNotes(objectDTO.getNotes());
        order.setDeleted(objectDTO.isDeleted());
        order.setDeletedAt(objectDTO.getDeletedAt());
        order.setCreatedAt(objectDTO.getCreatedAt());
        order.setUpdatedAt(objectDTO.getUpdatedAt());
        order.setCreatedBy(objectDTO.getCreatedBy());
        order.setUpdatedBy(objectDTO.getUpdatedBy());
        order.setCustomer(customerMapper.mapToEntity(objectDTO.getCustomerDTO()));
        order.setStatus(orderStatusMapper.mapToEntity(objectDTO.getOrderStatusDTO()));
        order.setCurrency(currencyMapper.mapToEntity(objectDTO.getCurrencyDTO()));
        order.setDeliveryAddress(addressMapper.mapToEntity(objectDTO.getDeliveryAddressDTO()));
        order.setOrderItems(
                Set.copyOf(objectDTO.getOrderItemsDTO().stream().map(orderItemMapper::mapToEntity).toList()));
        order.setDiscounts(Set.copyOf(
                objectDTO.getDiscountsDTO().stream().map(discountMapper::mapToEntity).toList()));

        return order;
    }

    @Override
    public OrderDTO mapToDTO(Order objectEntity) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderId(objectEntity.getOrderId());
        orderDTO.setOrderDate(objectEntity.getOrderDate());
        orderDTO.setSubtotal(objectEntity.getSubtotal());
        orderDTO.setTaxAmount(objectEntity.getTaxAmount());
        orderDTO.setTotalAmount(objectEntity.getTotalAmount());
        orderDTO.setNotes(objectEntity.getNotes());
        orderDTO.setDeleted(objectEntity.isDeleted());
        orderDTO.setDeletedAt(objectEntity.getDeletedAt());
        orderDTO.setCreatedAt(objectEntity.getCreatedAt());
        orderDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        orderDTO.setUpdatedBy(objectEntity.getUpdatedBy());
        orderDTO.setCustomerDTO(customerMapper.mapToDTO(objectEntity.getCustomer()));
        orderDTO.setOrderStatusDTO(orderStatusMapper.mapToDTO(objectEntity.getStatus()));
        orderDTO.setCurrencyDTO(currencyMapper.mapToDTO(objectEntity.getCurrency()));
        orderDTO.setDeliveryAddressDTO(addressMapper.mapToDTO(objectEntity.getDeliveryAddress()));
        orderDTO.setOrderItemsDTO(
                Set.copyOf(objectEntity.getOrderItems().stream().map(orderItemMapper::mapToDTO).toList()));
        orderDTO.setDiscountsDTO(Set.copyOf(
                objectEntity.getDiscounts().stream().map(discountMapper::mapToDTO).toList()));

        return orderDTO;
    }
}
