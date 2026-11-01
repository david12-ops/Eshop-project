package com.example.service_impl;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.AddressDTO;
import com.example.dto.CurrencyDTO;
import com.example.dto.CustomerDTO;
import com.example.dto.DiscountDTO;
import com.example.dto.OrderDTO;
import com.example.dto.OrderStatusDTO;
import com.example.exception.BadRequestException;
import com.example.model.Address;
import com.example.model.Currency;
import com.example.model.Customer;
import com.example.model.Discount;
import com.example.model.MyUserDetail;
import com.example.model.Order;
import com.example.model.OrderStatus;
import com.example.repository.OrderRepository;
import com.example.service_interface.AddressService;
import com.example.service_interface.CurrencyService;
import com.example.service_interface.CustomerService;
import com.example.service_interface.OrderService;
import com.example.service_interface.OrderStatusService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderStatusService orderStatusService;
    private final CurrencyService currencyService;
    private final AddressService addressService;

    private final ObjectMapper<Customer, CustomerDTO> customerMapper;
    private final ObjectMapper<OrderStatus, OrderStatusDTO> orderStatusMapper;
    private final ObjectMapper<Currency, CurrencyDTO> currencyMapper;
    private final ObjectMapper<Address, AddressDTO> addressMapper;
    private final ObjectMapper<Order, OrderDTO> orderMapper;
    private final ObjectMapper<Discount, DiscountDTO> discountMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
            CustomerService customerService, OrderStatusService orderStatusService,
            CurrencyService currencyService, AddressService addressService,
            ObjectMapper<Customer, CustomerDTO> customerMapper,
            ObjectMapper<OrderStatus, OrderStatusDTO> orderStatusMapper,
            ObjectMapper<Currency, CurrencyDTO> currencyMapper, ObjectMapper<Address, AddressDTO> addressMapper,
            ObjectMapper<Order, OrderDTO> orderMapper, ObjectMapper<Discount, DiscountDTO> discountMapper) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
        this.orderStatusService = orderStatusService;
        this.currencyService = currencyService;
        this.addressService = addressService;
        this.customerMapper = customerMapper;
        this.orderStatusMapper = orderStatusMapper;
        this.currencyMapper = currencyMapper;
        this.addressMapper = addressMapper;
        this.orderMapper = orderMapper;
        this.discountMapper = discountMapper;
    }

    @Override
    public List<OrderDTO> getOrders(MyUserDetail userDetail) {
        return orderRepository.findAll().stream()
                .map(orderMapper::mapToDTO).toList();
    }

    @Override
    public OrderDTO saveOrder(OrderDTO orderDTO, MyUserDetail userDetail) {
        if (orderDTO == null) {
            throw new BadRequestException("Order must not be null");
        }

        orderDTO.setCreatedAt(Instant.now());
        orderDTO.setUpdatedAt(Instant.now());

        orderDTO.setCreatedBy(userDetail.getUserId());
        orderDTO.setUpdatedBy(userDetail.getUserId());

        orderDTO.setDeleted(false);
        orderDTO.setDeletedAt(null);

        Order savedOrder = orderRepository.save(orderMapper.mapToEntity(orderDTO));

        return orderMapper.mapToDTO(savedOrder);
    }

    @Override
    @Transactional
    public OrderDTO getOrderWithItems(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderRepository.findByIdWithItems(id).map(orderWithItems -> orderMapper.mapToDTO(orderWithItems))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    // Soft deletion
    @Override
    public void deleteOrderById(Integer id, MyUserDetail userDetail) {
        Order orderToUpdate = getOrderEntityById(id);

        if (orderToUpdate.isDeleted())
            return;

        orderToUpdate.setUpdatedAt(Instant.now());
        orderToUpdate.setUpdatedBy(userDetail.getUserId());

        orderToUpdate.setDeleted(true);
        orderToUpdate.setDeletedAt(Instant.now());

        orderRepository.save(orderToUpdate);
        // orderRepository.softDeleteById(existingOrder.getOrderId());
    }

    @Override
    public OrderDTO editOrder(Integer id, OrderDTO orderDTO, MyUserDetail userDetail) {
        if (orderDTO == null) {
            throw new BadRequestException("Order to edit must not be null");
        }

        Order orderToUpdate = getOrderEntityById(id);

        Customer customer = null;
        OrderStatus orderStatus = null;
        Currency currency = null;
        Address address = null;
        // TODO Check
        Set<Discount> discounts = null;

        if (orderDTO.getCustomerDTO() != null) {
            customer = customerMapper.mapToEntity(
                    customerService.getCustomerById(orderDTO.getCustomerDTO().getCustomerId(), userDetail));
        }

        if (orderDTO.getOrderStatusDTO() != null) {
            orderStatus = orderStatusMapper
                    .mapToEntity(orderStatusService.getOrderStatusById(orderDTO.getOrderStatusDTO().getOrderStatusId(),
                            userDetail));
        }

        if (orderDTO.getCurrencyDTO() != null) {
            currency = currencyMapper.mapToEntity(
                    currencyService.getCurrencyByCode(orderDTO.getCurrencyDTO().getCurrencyCode(), userDetail));
        }

        if (orderDTO.getDeliveryAddressDTO() != null) {
            address = addressMapper.mapToEntity(
                    addressService.getAddressById(orderDTO.getDeliveryAddressDTO().getAddressId(), userDetail));
        }

        if (orderDTO.getDiscountsDTO() != null
                && !orderDTO.getDiscountsDTO().isEmpty()) {
            discounts = orderDTO.getDiscountsDTO().stream()
                    .map(discountMapper::mapToEntity)
                    .collect(Collectors.toSet());
        }

        orderToUpdate.setCustomer(customer);
        orderToUpdate.setStatus(orderStatus);
        orderToUpdate.setCurrency(currency);
        orderToUpdate.setDeliveryAddress(address);
        orderToUpdate.setDiscounts(discounts);

        orderToUpdate.setOrderDate(orderDTO.getOrderDate());
        orderToUpdate.setSubtotal(orderDTO.getSubtotal());
        orderToUpdate.setTaxAmount(orderDTO.getTaxAmount());
        orderToUpdate.setTotalAmount(orderDTO.getTotalAmount());
        orderToUpdate.setNotes(orderDTO.getNotes());

        return orderMapper.mapToDTO(orderRepository.save(orderToUpdate));
    }

    private Order getOrderEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderRepository.findByIdWithItems(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }
}
