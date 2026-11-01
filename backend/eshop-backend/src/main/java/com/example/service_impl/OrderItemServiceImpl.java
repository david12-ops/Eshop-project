package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.OrderItemDTO;
import com.example.exception.BadRequestException;
import com.example.model.MyUserDetail;
import com.example.model.OrderItem;
import com.example.repository.OrderItemRepository;
import com.example.service_interface.OrderItemService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    private final ObjectMapper<OrderItem, OrderItemDTO> orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
            ObjectMapper<OrderItem, OrderItemDTO> orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
    }

    @Override
    public OrderItemDTO getOrderItemById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderItemRepository.findById(id).map(orderItem -> orderItemMapper.mapToDTO(orderItem))
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
    }

    @Override
    public List<OrderItemDTO> getAllOrderItems(MyUserDetail userDetail) {
        return orderItemRepository.findAll().stream()
                .map(orderItemMapper::mapToDTO).toList();
    }

    @Override
    public OrderItemDTO saveOrderItem(OrderItemDTO orderItemDTO, MyUserDetail userDetail) {
        if (orderItemDTO == null) {
            throw new BadRequestException("Order item must not be null");
        }

        orderItemDTO.setDeleted(false);
        orderItemDTO.setDeletedAt(null);

        OrderItem savedOrderItem = orderItemRepository
                .save(orderItemMapper.mapToEntity(orderItemDTO));

        return orderItemMapper.mapToDTO(orderItemRepository.save(savedOrderItem));
    }

    // Soft deletion
    // TODO Deletion update
    @Override
    public void deleteOrderItemById(Integer id, MyUserDetail userDetail) {
        OrderItem orderItemToEdit = getOrderItemEntityById(id);

        if (orderItemToEdit.isDeleted())
            return;

        orderItemToEdit.setDeleted(true);
        orderItemToEdit.setDeletedAt(Instant.now());

        orderItemRepository.save(orderItemToEdit);
        // orderItemRepository.softDeleteById(existingOrderItem.getOrderItemId());
    }

    @Override
    public OrderItemDTO editOrderItem(Integer id, OrderItemDTO orderItemDTO, MyUserDetail userDetail) {
        if (orderItemDTO == null) {
            throw new BadRequestException("Order item to edit must not be null");
        }

        OrderItem orderItemToUpdate = getOrderItemEntityById(id);

        orderItemToUpdate.setLineTotal(orderItemDTO.getLineTotal());
        orderItemToUpdate.setQuantity(orderItemDTO.getQuantity());
        orderItemToUpdate.setNotes(orderItemDTO.getNotes());
        orderItemToUpdate.setUnitPrice(orderItemDTO.getUnitPrice());
        orderItemToUpdate.setDeleted(false);
        orderItemToUpdate.setDeletedAt(null);

        return orderItemMapper.mapToDTO(orderItemRepository.save(orderItemToUpdate));
    }

    private OrderItem getOrderItemEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order item not found with id: " + id));
    }
}
