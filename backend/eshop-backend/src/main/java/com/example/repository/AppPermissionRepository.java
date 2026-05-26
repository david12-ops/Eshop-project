package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.AppPermission;
import com.example.model.keys.AppPermissionId;

public interface AppPermissionRepository extends JpaRepository<AppPermission, AppPermissionId> {
}
