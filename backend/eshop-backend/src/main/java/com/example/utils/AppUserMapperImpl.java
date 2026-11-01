package com.example.utils;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.example.dto.AppUserDTO;
import com.example.dto.RoleDTO;
import com.example.exception.BadRequestException;
import com.example.model.AppUser;
import com.example.model.Role;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class AppUserMapperImpl implements ObjectMapper<AppUser, AppUserDTO> {
    private final ObjectMapper<Role, RoleDTO> roleMapper;

    public AppUserMapperImpl(ObjectMapper<Role, RoleDTO> roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    @NonNull
    public AppUser mapToEntity(AppUserDTO objectDTO) {
        if (objectDTO == null) {
            throw new BadRequestException(
                    "DTO object is required");
        }

        if ((objectDTO.getUsername() == null || objectDTO.getUsername().isBlank()) ||
                (objectDTO.getEmail() == null || objectDTO.getEmail().isBlank()) ||
                (objectDTO.getPassword() == null || objectDTO.getPassword().isBlank()) || objectDTO.getRoleDTO() == null
                || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Username, email, password, role and deletion state are required");
        }

        AppUser appUser = new AppUser();

        appUser.setUsername(objectDTO.getUsername());
        appUser.setEmail(objectDTO.getEmail());
        appUser.setPassword(objectDTO.getPassword());
        appUser.setRole(roleMapper.mapToEntity(objectDTO.getRoleDTO()));
        appUser.setDeleted(objectDTO.isDeleted());
        appUser.setDeletedAt(objectDTO.getDeletedAt());
        appUser.setCreatedAt(objectDTO.getCreatedAt());
        appUser.setUpdatedAt(objectDTO.getUpdatedAt());
        appUser.setCreatedBy(objectDTO.getCreatedBy());
        appUser.setUpdatedBy(objectDTO.getUpdatedBy());

        return appUser;
    }

    @Override
    public AppUserDTO mapToDTO(AppUser objectEntity) {
        AppUserDTO appUserDTO = new AppUserDTO();

        appUserDTO.setAppUserId(objectEntity.getAppUserId());
        appUserDTO.setUsername(objectEntity.getUsername());
        appUserDTO.setEmail(objectEntity.getEmail());
        appUserDTO.setPassword(null);
        appUserDTO.setRoleDTO(roleMapper.mapToDTO(objectEntity.getRole()));
        appUserDTO.setDeleted(objectEntity.isDeleted());
        appUserDTO.setDeletedAt(objectEntity.getDeletedAt());
        appUserDTO.setCreatedAt(objectEntity.getCreatedAt());
        appUserDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        appUserDTO.setCreatedBy(objectEntity.getCreatedBy());
        appUserDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return appUserDTO;
    }
}
