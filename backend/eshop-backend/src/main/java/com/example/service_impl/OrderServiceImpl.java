package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.Currency;
import com.example.model.Customer;
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
    public List<Order> getOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order saveOrder(Order order) {
        if (order == null) {
            throw new BadRequestException("Order must not be null");
        }

        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order getOrderWithItems(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    // Soft deletion
    @Override
    public void deleteOrderById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        orderRepository.findById(id).ifPresent(order -> {
            if (order.getDeleted())
                return;

            orderRepository.softDeleteById(id);
        });
    }

    @Override
    public Order editOrder(Integer id, Order order) {
        if (id == null || order == null) {
            throw new BadRequestException("ID or order to edit must not be null");
        }

        return orderRepository.findById(id).map(existingOrder -> {
            Customer customer = null;
            OrderStatus orderStatus = null;
            Currency currency = null;
            Address address = null;
            // Set<Discount> discounts = order.getDiscounts(); Missing gui

            if (order.getCustomer() != null
                    && order.getCustomer().getCustomerId() != null) {

                customer = customerService.getCustomerById(
                        order.getCustomer().getCustomerId());
            }

            if (order.getStatus() != null
                    && order.getStatus().getId() != null) {

                orderStatus = orderStatusService.getOrderStatusById(
                        order.getStatus().getId());
            }

            if (order.getCurrency() != null
                    && order.getCurrency().getCurrencyCode() != null) {

                currency = currencyService.getCurrencyById(
                        order.getCurrency().getCurrencyCode());
            }

            if (order.getDeliveryAddress() != null
                    && order.getDeliveryAddress().getId() != null) {

                address = addressService.getAddressById(
                        order.getDeliveryAddress().getId());
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
        }).orElse(null);
    }
}
