package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.OrderStatusDTO;
import com.example.exception.BadRequestException;
import com.example.model.MyUserDetail;
import com.example.model.OrderStatus;
import com.example.repository.OrderStatusRepository;
import com.example.service_interface.OrderStatusService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    private final ObjectMapper<OrderStatus, OrderStatusDTO> orderStatusMapper;

    public OrderStatusServiceImpl(OrderStatusRepository orderStatusRepository,
            ObjectMapper<OrderStatus, OrderStatusDTO> orderStatusMapper) {
        this.orderStatusRepository = orderStatusRepository;
        this.orderStatusMapper = orderStatusMapper;
    }

    @Override
    public OrderStatusDTO getOrderStatusById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderStatusRepository.findById(id).map(orderStatus -> orderStatusMapper.mapToDTO(orderStatus))
                .orElseThrow(() -> new ResourceNotFoundException("Order status not found with id: " + id));
    }

    @Override
    public List<OrderStatusDTO> getOrderStatuses(MyUserDetail userDetail) {
        return orderStatusRepository.findAll().stream()
                .map(orderStatusMapper::mapToDTO).toList();
    }

    @Override
    public OrderStatusDTO saveOrderStatus(OrderStatusDTO orderStatusDTO, MyUserDetail userDetail) {
        if (orderStatusDTO == null) {
            throw new BadRequestException("Order status must not be null");
        }

        orderStatusDTO.setCreatedAt(Instant.now());
        orderStatusDTO.setUpdatedAt(Instant.now());

        orderStatusDTO.setCreatedBy(userDetail.getUserId());
        orderStatusDTO.setUpdatedBy(userDetail.getUserId());

        orderStatusDTO.setDeleted(false);
        orderStatusDTO.setDeletedAt(null);

        OrderStatus savedOrderStatus = orderStatusRepository
                .save(orderStatusMapper.mapToEntity(orderStatusDTO));

        return orderStatusMapper.mapToDTO(orderStatusRepository.save(savedOrderStatus));
    }

    // Soft deletion
    @Override
    public void deleteOrderStatusById(Integer id, MyUserDetail userDetail) {
        OrderStatus orderStatusToDelete = getOrderStatusEntityById(id);

        if (orderStatusToDelete.isDeleted())
            return;

        orderStatusToDelete.setUpdatedAt(Instant.now());
        orderStatusToDelete.setUpdatedBy(userDetail.getUserId());

        orderStatusToDelete.setDeleted(true);
        orderStatusToDelete.setDeletedAt(Instant.now());

        orderStatusRepository.save(orderStatusToDelete);
        // orderStatusRepository.softDeleteById(existingOrderStatus.getOrderStatusId());
    }

    @Override
    public OrderStatusDTO editOrderStatus(Integer id, OrderStatusDTO orderStatusDTO, MyUserDetail userDetail) {
        if (orderStatusDTO == null) {
            throw new BadRequestException("Order status to edit must not be null");
        }

        OrderStatus orderStatusToUpdate = getOrderStatusEntityById(id);

        orderStatusToUpdate.setStatusName(orderStatusDTO.getStatusName());
        orderStatusToUpdate.setStatusType(orderStatusDTO.getStatusType());
        orderStatusToUpdate.setStatusDescription(orderStatusDTO.getStatusDescription());
        orderStatusToUpdate.setUpdatedAt(Instant.now());
        orderStatusToUpdate.setUpdatedBy(userDetail.getUserId());
        orderStatusToUpdate.setDeleted(false);
        orderStatusToUpdate.setDeletedAt(null);

        return orderStatusMapper.mapToDTO(orderStatusRepository.save(orderStatusToUpdate));
    }

    private OrderStatus getOrderStatusEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return orderStatusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order status not found with id: " + id));
    }
}
