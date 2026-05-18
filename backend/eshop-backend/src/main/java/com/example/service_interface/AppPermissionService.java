package com.example.service_interface;

import com.example.model.AppPermission;
import com.example.model.keys.AppPermissionId;

import java.util.List;

public interface AppPermissionService {

    void savePermission(AppPermission permission);

    AppPermission findById(AppPermissionId id);

    List<AppPermission> findAll();

    void deleteById(AppPermissionId id);
}
