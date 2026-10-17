package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.OrderDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.Currency;
import com.example.model.Customer;
import com.example.model.MyUserDetail;
import com.example.model.Order;
import com.example.model.OrderStatus;
import com.example.repository.OrderRepository;
import com.example.service_interface.AddressService;
import com.example.service_interface.CurrencyService;
import com.example.service_interface.CustomerService;
import com.example.service_interface.OrderService;
import com.example.service_interface.OrderStatusService;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderStatusService orderStatusService;
    private final CurrencyService currencyService;
    private final AddressService addressService;

    public OrderServiceImpl(OrderRepository orderRepository,
            CustomerService customerService, OrderStatusService orderStatusService,
            CurrencyService currencyService, AddressService addressService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderStatusService = orderStatusService;
        this.currencyService = currencyService;
        this.addressService = addressService;
    }

    @Override
    public List<OrderDTO> getOrders(MyUserDetail userDetail) {
        return orderRepository.findAll();
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO, MyUserDetail userDetail) {
        if (orderDTO == null) {
            throw new BadRequestException("Order must not be null");
        }

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public OrderDTO getOrderWithItems(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    // Soft deletion
    @Override
    public void deleteOrderById(Integer id, MyUserDetail userDetail) {
        Order existingOrder = getOrderWithItems(id);

        if (existingOrder.isDeleted())
            return;

        orderRepository.softDeleteById(existingOrder.getOrderId());
    }

    @Override
    public OrderDTO editOrder(Integer id, OrderDTO orderDTO, MyUserDetail userDetail) {
        if (orderDTO == null) {
            throw new BadRequestException("Order to edit must not be null");
        }

        Order existingOrder = getOrderWithItems(id);

        Customer customer = null;
        OrderStatus orderStatus = null;
        Currency currency = null;
        Address address = null;
        // Set<Discount> discounts = order.getDiscounts(); Missing gui

        if (order.getCustomer() != null && order.getCustomer().getCustomerId() != null) {
            customer = customerService.getCustomerById(order.getCustomer().getCustomerId());
        }

        if (order.getStatus() != null && order.getStatus().getOrderStatusId() != null) {
            orderStatus = orderStatusService.getOrderStatusById(order.getStatus().getOrderStatusId());
        }

        if (order.getCurrency() != null && order.getCurrency().getCurrencyCode() != null) {
            currency = currencyService.getCurrencyByCode(order.getCurrency().getCurrencyCode());
        }

        if (order.getDeliveryAddress() != null && order.getDeliveryAddress().getAddressId() != null) {
            address = addressService.getAddressById(order.getDeliveryAddress().getAddressId());
        }

        existingOrder.setCustomer(customer);
        existingOrder.setStatus(orderStatus);
        existingOrder.setCurrency(currency);
        existingOrder.setDeliveryAddress(address);

        existingOrder.setOrderDate(order.getOrderDate());
        existingOrder.setSubtotal(order.getSubtotal());
        existingOrder.setTaxAmount(order.getTaxAmount());
        existingOrder.setTotalAmount(order.getTotalAmount());
        existingOrder.setNotes(order.getNotes());

        return orderRepository.save(existingOrder);
    }
}
