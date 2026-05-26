package com.example.service_interface;

import com.example.model.AppPermission;
import com.example.model.Role;
import com.example.model.keys.AppPermissionId;

import java.util.List;

public interface AppPermissionService {

    void saveAppPermission(AppPermission permission);

    AppPermission getAppPermissionById(AppPermissionId id);

    List<AppPermission> findAll();

    List<Role> findAllRoles();

    void editAppPermission(AppPermissionId id, AppPermission permission);

    void deleteAppPermissionById(AppPermissionId id);
}
