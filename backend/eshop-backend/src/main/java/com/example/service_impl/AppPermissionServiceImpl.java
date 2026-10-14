package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.AppPermission;
import com.example.model.keys.AppPermissionId;
import com.example.repository.AppPermissionRepository;
import com.example.service_interface.AppPermissionService;

@Service
public class AppPermissionServiceImpl implements AppPermissionService {
    private final AppPermissionRepository appPermissionRepository;

    public AppPermissionServiceImpl(
            AppPermissionRepository appPermissionRepository) {
        this.appPermissionRepository = appPermissionRepository;
    }

    @Override
    public AppPermission saveAppPermission(AppPermission permission) {
        if (permission == null) {
            throw new BadRequestException("Permission must not be null");
        }

        return appPermissionRepository.save(permission);
    }

    @Override
    public AppPermission getAppPermissionById(AppPermissionId id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return appPermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
    }

    @Override
    public List<AppPermission> getAllAppPermissions() {
        return appPermissionRepository.findAll();
    }

    @Override
    public AppPermission editAppPermission(AppPermissionId id, AppPermission permission) {
        if (permission == null) {
            throw new BadRequestException("Permission to edit must not be null");
        }

        AppPermission existingPAppPermission = getAppPermissionById(id);

        existingPAppPermission.setResourceType(permission.getResourceType());
        existingPAppPermission.setOperationType(permission.getOperationType());

        return appPermissionRepository.save(existingPAppPermission);
    }

    // Soft deletion
    @Override
    public void deleteAppPermissionById(AppPermissionId id) {
        AppPermission existingPAppPermission = getAppPermissionById(id);

        if (existingPAppPermission.getDeleted())
            return;

        AppPermissionId appPermissionId = new AppPermissionId(
                existingPAppPermission.getRole().getRoleId(),
                existingPAppPermission.getResourceType(),
                existingPAppPermission.getOperationType());
        appPermissionRepository.softDeleteById(appPermissionId);
    }
}
