package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.ResourceNotFoundException;
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
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void saveOrder(Order order) {
        if (order == null)
            return;

        orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order getOrderWithItems(Integer id) {
        return orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Override
    public void deleteOrderById(Integer id) {
        if (id == null)
            return;

        // zatím ne
        orderRepository.findById(id).ifPresent(order -> {
            if (order.getDeleted())
                return; // tady jen soft kvůli klíčům

            orderRepository.softDeleteById(id);
        });
    }

    @Override
    public void editOrder(Integer id, Order order) {
        if (id == null || order == null)
            return;

        orderRepository.findById(id).ifPresent(existingOrder -> {

            Customer customer = null;
            OrderStatus orderStatus = null;
            Currency currency = null;
            Address address = null;
            // Set<Discount> discounts = order.getDiscounts(); pro tohlr neni ui

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

            orderRepository.save(existingOrder);
        });
    }
}
