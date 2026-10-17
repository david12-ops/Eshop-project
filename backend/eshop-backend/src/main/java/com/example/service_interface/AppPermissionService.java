package com.example.service_interface;

import com.example.dto.AppPermissionDTO;
import com.example.model.MyUserDetail;
import com.example.model.keys.AppPermissionId;

import java.util.List;

public interface AppPermissionService {

    AppPermissionDTO saveAppPermission(AppPermissionDTO permissionDTO, MyUserDetail userDetail);

    AppPermissionDTO getAppPermissionById(AppPermissionId id, MyUserDetail userDetail);

    List<AppPermissionDTO> getAllAppPermissions(MyUserDetail userDetail);

    AppPermissionDTO editAppPermission(AppPermissionId id, AppPermissionDTO permissionDTO, MyUserDetail userDetail);

    void deleteAppPermissionById(AppPermissionId id, MyUserDetail userDetail);
}
