package com.example.utils;

import org.springframework.stereotype.Component;

import com.example.dto.AppPermissionDTO;
import com.example.dto.AppUserDTO;
import com.example.dto.RoleDTO;
import com.example.exception.BadRequestException;
import com.example.model.AppPermission;
import com.example.model.AppUser;
import com.example.model.Role;
import com.example.utils.utils_interface.ObjectMapper;

import java.util.Set;

import org.springframework.lang.NonNull;

@Component
public class RoleMapperImpl implements ObjectMapper<Role, RoleDTO> {
    private final ObjectMapper<AppUser, AppUserDTO> appUserMapper;
    private final ObjectMapper<AppPermission, AppPermissionDTO> appPermissionMapper;

    public RoleMapperImpl(ObjectMapper<AppUser, AppUserDTO> appUserMapper,
            ObjectMapper<AppPermission, AppPermissionDTO> appPermissionMapper) {
        this.appUserMapper = appUserMapper;
        this.appPermissionMapper = appPermissionMapper;
    }

    @Override
    @NonNull
    public Role mapToEntity(RoleDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if ((objectDTO.getRoleName() == null || objectDTO.getRoleName().isBlank()) ||
                objectDTO.getRoleType() == null || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Role name, role type and deletion state are required");
        }

        Role role = new Role();

        role.setRoleName(objectDTO.getRoleName());
        role.setRoleType(objectDTO.getRoleType());
        role.setRoleDescription(objectDTO.getRoleDescription());
        role.setDeleted(objectDTO.isDeleted());
        role.setDeletedAt(objectDTO.getDeletedAt());
        role.setCreatedAt(objectDTO.getCreatedAt());
        role.setUpdatedAt(objectDTO.getUpdatedAt());
        role.setCreatedBy(objectDTO.getCreatedBy());
        role.setUpdatedBy(objectDTO.getUpdatedBy());

        return role;
    }

    @Override
    public RoleDTO mapToDTO(Role objectEntity) {
        RoleDTO roleDTO = new RoleDTO();

        roleDTO.setRoleId(objectEntity.getRoleId());
        roleDTO.setRoleName(objectEntity.getRoleName());
        roleDTO.setRoleType(objectEntity.getRoleType());
        roleDTO.setRoleDescription(objectEntity.getRoleDescription());
        roleDTO.setDeleted(objectEntity.isDeleted());
        roleDTO.setDeletedAt(objectEntity.getDeletedAt());
        roleDTO.setCreatedAt(objectEntity.getCreatedAt());
        roleDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        roleDTO.setCreatedBy(objectEntity.getCreatedBy());
        roleDTO.setUpdatedBy(objectEntity.getUpdatedBy());
        roleDTO.setUsersDTO(
                Set.copyOf(objectEntity.getUsers().stream().map(appUserMapper::mapToDTO).toList()));
        roleDTO.setPermissionsDTO(Set.copyOf(objectEntity.getPermissions().stream()
                .map(appPermissionMapper::mapToDTO).toList()));

        return roleDTO;
    }
}
