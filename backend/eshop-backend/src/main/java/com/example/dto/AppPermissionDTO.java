package com.example.dto;

import java.time.Instant;
import com.example.model.enums.OperationType;
import com.example.model.enums.ResourceType;

public class AppPermissionDTO {
    private RoleDTO roleDTO;
    private ResourceType resourceType;
    private OperationType operationType;
    private Boolean deleted = false;
    private Instant deletedAt;
    private Instant createdAt;
    private Instant updatedAt;
    private Integer createdBy;
    private Integer updatedBy;

    public AppPermissionDTO() {
    }

    public RoleDTO getRoleDTO() {
        return roleDTO;
    }

    public void setRole(RoleDTO roleDTO) {
        this.roleDTO = roleDTO;
    }

    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceType resourceType) {
        this.resourceType = resourceType;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
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

    @Override
    public String toString() {
        return "AppPermission{" +
                "role=" + roleDTO.toString() +
                ", resourceType=" + resourceType +
                ", operationType=" + operationType +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", createdBy=" + createdBy +
                ", updatedBy=" + updatedBy +
                '}';
    }
}
