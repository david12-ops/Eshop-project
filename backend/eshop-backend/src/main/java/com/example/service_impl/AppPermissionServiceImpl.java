package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.AppPermission;
import com.example.model.Role;
import com.example.model.keys.AppPermissionId;
import com.example.repository.AppPermissionRepository;
import com.example.repository.RoleRepository;
import com.example.service_interface.AppPermissionService;

@Service
public class AppPermissionServiceImpl implements AppPermissionService {
    private final AppPermissionRepository appPermissionRepository;
    private final RoleRepository roleRepository;

    public AppPermissionServiceImpl(
            AppPermissionRepository appPermissionRepository,
            RoleRepository roleRepository) {

        this.appPermissionRepository = appPermissionRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void saveAppPermission(AppPermission permission) {
        if (permission == null)
            return;

        appPermissionRepository.save(permission);
    }

    @Override
    public AppPermission getAppPermissionById(AppPermissionId id) {
        return appPermissionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + id));
    }

    @Override
    public List<AppPermission> findAll() {
        return appPermissionRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void editAppPermission(AppPermissionId id, AppPermission permission) {
        if (id == null || permission == null)
            return;

        appPermissionRepository.findById(id).ifPresent(existingPAppPermission -> {

            existingPAppPermission.setResourceType(permission.getResourceType());
            existingPAppPermission.setOperationType(permission.getOperationType());

            appPermissionRepository.save(existingPAppPermission);

        });
    }

    @Override
    public void deleteAppPermissionById(AppPermissionId id) {
        if (id == null)
            return;

        appPermissionRepository.findById(id).ifPresent(appPermission -> appPermissionRepository.deleteById(id));
    }
}
