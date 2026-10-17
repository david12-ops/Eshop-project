package com.example.dto;

import com.example.model.enums.RoleType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class RoleDTO {
    private String roleName;
    private RoleType roleType;
    private String roleDescription;
    private Boolean deleted = false;
    private Instant deletedAt;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer createdBy;
    private Integer updatedBy;
    private Set<AppUserDTO> usersDTO = new HashSet<>();
    private Set<AppPermissionDTO> permissionsDTO = new HashSet<>();

    public RoleDTO() {
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Integer updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Set<AppUserDTO> getUsersDTO() {
        return usersDTO;
    }

    public void setUsersDTO(Set<AppUserDTO> usersDTO) {
        this.usersDTO = usersDTO;
    }

    public Set<AppPermissionDTO> getPermissionsDTO() {
        return permissionsDTO;
    }

    public void setPermissionsDTO(Set<AppPermissionDTO> permissionsDTO) {
        this.permissionsDTO = permissionsDTO;
    }
}
