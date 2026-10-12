package com.example.service_interface;

import com.example.model.AppPermission;
import com.example.model.keys.AppPermissionId;

import java.util.List;

public interface AppPermissionService {

    AppPermission saveAppPermission(AppPermission permission);

    AppPermission getAppPermissionById(AppPermissionId id);

    List<AppPermission> getAllAppPermissions();

    AppPermission editAppPermission(AppPermissionId id, AppPermission permission);

    void deleteAppPermissionById(AppPermissionId id);
}
