package com.example.service_impl;

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

@Service
public class AppPermissionServiceImpl implements AppPermissionService {
    private final AppPermissionRepository appPermissionRepository;

    public AppPermissionServiceImpl(
            AppPermissionRepository appPermissionRepository) {
        this.appPermissionRepository = appPermissionRepository;
    }

    @Override
    public AppPermissionDTO saveAppPermission(AppPermissionDTO appPermissionDTO, MyUserDetail userDetail) {
        if (appPermissionDTO == null) {
            throw new BadRequestException("Permission must not be null");
        }

        return appPermissionRepository.save(appPermissionDTO);
    }

    @Override
    public AppPermissionDTO getAppPermissionById(AppPermissionId id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return appPermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
    }

    @Override
    public List<AppPermissionDTO> getAllAppPermissions(MyUserDetail userDetail) {
        return appPermissionRepository.findAll();
    }

    @Override
    public AppPermissionDTO editAppPermission(AppPermissionId id, AppPermissionDTO appPermissionDTO,
            MyUserDetail userDetail) {
        if (appPermissionDTO == null) {
            throw new BadRequestException("Permission to edit must not be null");
        }

        AppPermissionDTO existingPAppPermissionDTO = getAppPermissionById(id);

        existingPAppPermissionDTO.setResourceType(appPermissionDTO.getResourceType());
        existingPAppPermissionDTO.setOperationType(appPermissionDTO.getOperationType());

        return appPermissionRepository.save(existingPAppPermissionDTO);
    }

    // Soft deletion
    @Override
    public void deleteAppPermissionById(AppPermissionId id, MyUserDetail userDetail) {
        AppPermission existingPAppPermission = getAppPermissionById(id);

        if (existingPAppPermission.isDeleted())
            return;

        AppPermissionId appPermissionId = new AppPermissionId(
                existingPAppPermission.getRole().getRoleId(),
                existingPAppPermission.getResourceType(),
                existingPAppPermission.getOperationType());
        appPermissionRepository.softDeleteById(appPermissionId);
    }
}
