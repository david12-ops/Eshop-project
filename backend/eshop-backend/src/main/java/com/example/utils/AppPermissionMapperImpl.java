package com.example.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.dto.AppPermissionDTO;
import com.example.dto.RoleDTO;
import com.example.exception.BadRequestException;
import com.example.model.AppPermission;
import com.example.model.Role;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class AppPermissionMapperImpl implements ObjectMapper<AppPermission, AppPermissionDTO> {
    private final ObjectMapper<Role, RoleDTO> roleMapper;

    public AppPermissionMapperImpl(ObjectMapper<Role, RoleDTO> roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    @NonNull
    public AppPermission mapToEntity(AppPermissionDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if (objectDTO.getRoleDTO() == null || objectDTO.getResourceType() == null ||
                objectDTO.getOperationType() == null || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Role, resource type, operation type and deletion state are required");
        }

        AppPermission appPermission = new AppPermission();

        appPermission.setRole(roleMapper.mapToEntity(objectDTO.getRoleDTO()));
        appPermission.setResourceType(objectDTO.getResourceType());
        appPermission.setOperationType(objectDTO.getOperationType());
        appPermission.setDeleted(objectDTO.isDeleted());
        appPermission.setDeletedAt(objectDTO.getDeletedAt());
        appPermission.setCreatedAt(objectDTO.getCreatedAt());
        appPermission.setUpdatedAt(objectDTO.getUpdatedAt());
        appPermission.setCreatedBy(objectDTO.getCreatedBy());
        appPermission.setUpdatedBy(objectDTO.getUpdatedBy());

        return appPermission;
    }

    @Override
    public AppPermissionDTO mapToDTO(AppPermission objectEntity) {
        AppPermissionDTO appPermissionDTO = new AppPermissionDTO();

        appPermissionDTO.setRole(roleMapper.mapToDTO(objectEntity.getRole()));
        appPermissionDTO.setResourceType(objectEntity.getResourceType());
        appPermissionDTO.setOperationType(objectEntity.getOperationType());
        appPermissionDTO.setDeleted(objectEntity.isDeleted());
        appPermissionDTO.setDeletedAt(objectEntity.getDeletedAt());
        appPermissionDTO.setCreatedAt(objectEntity.getCreatedAt());
        appPermissionDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        appPermissionDTO.setCreatedBy(objectEntity.getCreatedBy());
        appPermissionDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return appPermissionDTO;
    }
}
