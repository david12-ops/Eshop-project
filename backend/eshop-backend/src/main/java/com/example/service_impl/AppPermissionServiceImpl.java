package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.AppPermissionDTO;
import com.example.exception.BadRequestException;
import com.example.model.AppPermission;
import com.example.model.MyUserDetail;
import com.example.model.keys.AppPermissionId;
import com.example.repository.AppPermissionRepository;
import com.example.service_interface.AppPermissionService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class AppPermissionServiceImpl implements AppPermissionService {
    private final AppPermissionRepository appPermissionRepository;

    private final ObjectMapper<AppPermission, AppPermissionDTO> appPermissionMapper;

    public AppPermissionServiceImpl(
            AppPermissionRepository appPermissionRepository,
            ObjectMapper<AppPermission, AppPermissionDTO> appPermissionMapper) {
        this.appPermissionRepository = appPermissionRepository;
        this.appPermissionMapper = appPermissionMapper;
    }

    @Override
    public AppPermissionDTO saveAppPermission(AppPermissionDTO appPermissionDTO, MyUserDetail userDetail) {
        if (appPermissionDTO == null) {
            throw new BadRequestException("Permission must not be null");
        }

        appPermissionDTO.setCreatedAt(Instant.now());
        appPermissionDTO.setUpdatedAt(Instant.now());

        appPermissionDTO.setCreatedBy(userDetail.getUserId());
        appPermissionDTO.setUpdatedBy(userDetail.getUserId());

        appPermissionDTO.setDeleted(false);
        appPermissionDTO.setDeletedAt(null);

        AppPermission savedAppPermission = appPermissionRepository
                .save(appPermissionMapper.mapToEntity(appPermissionDTO));

        return appPermissionMapper.mapToDTO(savedAppPermission);
    }

    @Override
    public AppPermissionDTO getAppPermissionById(AppPermissionId id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return appPermissionRepository.findById(id).map(appPermission -> appPermissionMapper.mapToDTO(appPermission))
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
    }

    @Override
    public List<AppPermissionDTO> getAllAppPermissions(MyUserDetail userDetail) {
        return appPermissionRepository.findAll().stream()
                .map(appPermissionMapper::mapToDTO).toList();
    }

    @Override
    public AppPermissionDTO editAppPermission(AppPermissionId id, AppPermissionDTO appPermissionDTO,
            MyUserDetail userDetail) {
        if (appPermissionDTO == null) {
            throw new BadRequestException("Permission to edit must not be null");
        }

        AppPermission appPermissionToDelete = getAppPermissionEntityById(id);

        appPermissionToDelete.setResourceType(appPermissionDTO.getResourceType());
        appPermissionToDelete.setOperationType(appPermissionDTO.getOperationType());
        appPermissionToDelete.setUpdatedAt(Instant.now());
        appPermissionToDelete.setUpdatedBy(userDetail.getUserId());
        appPermissionToDelete.setDeleted(false);
        appPermissionToDelete.setDeletedAt(null);

        return appPermissionMapper.mapToDTO(appPermissionRepository.save(appPermissionToDelete));
    }

    // Soft deletion
    @Override
    public void deleteAppPermissionById(AppPermissionId id, MyUserDetail userDetail) {
        AppPermission appPermissionToDelete = getAppPermissionEntityById(id);

        if (appPermissionToDelete.isDeleted())
            return;

        appPermissionToDelete.setUpdatedAt(Instant.now());
        appPermissionToDelete.setUpdatedBy(userDetail.getUserId());

        appPermissionToDelete.setDeleted(true);
        appPermissionToDelete.setDeletedAt(Instant.now());

        // AppPermissionId appPermissionId = new AppPermissionId(
        // appPermissionToDelete.getRole().getRoleId(),
        // appPermissionToDelete.getResourceType(),
        // appPermissionToDelete.getOperationType());

        appPermissionRepository.save(appPermissionToDelete);
        // appPermissionRepository.softDeleteById(appPermissionId);
    }

    private AppPermission getAppPermissionEntityById(AppPermissionId id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return appPermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
    }
}
