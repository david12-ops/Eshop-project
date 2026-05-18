package com.example.model.keys;

import com.example.model.enums.OperationType;
import com.example.model.enums.ResourceType;

import java.io.Serializable;
import java.util.Objects;

public class AppPermissionId implements Serializable {

    private Integer role;
    private ResourceType resourceType;
    private OperationType operationType;

    public AppPermissionId() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof AppPermissionId that))
            return false;

        return Objects.equals(role, that.role)
                && resourceType == that.resourceType
                && operationType == that.operationType;
    }

    public Integer getRole() {
        return role;
    }

    public void setRoleId(Integer role) {
        this.role = role;
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

    @Override
    public int hashCode() {
        return Objects.hash(role, resourceType, operationType);
    }
}
