package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.AppPermission;
import com.example.model.keys.AppPermissionId;
import com.example.repository.AppPermissionRepository;
import com.example.service_interface.AppPermissionService;

@Service
public class AppPermissionServiceImpl implements AppPermissionService {
    private final AppPermissionRepository appPermissionRepository;

    public AppPermissionServiceImpl(AppPermissionRepository appPermissionRepository) {
        this.appPermissionRepository = appPermissionRepository;
    }

    @Override
    public void savePermission(AppPermission permission) {
        if (permission == null)
            return;
    }

    @Override
    public AppPermission findById(AppPermissionId id) {
        if (id == null)
            return null;

        return appPermissionRepository.findById(id).orElse(null);
    }

    @Override
    public List<AppPermission> findAll() {
        return appPermissionRepository.findAll();
    }

    @Override
    public void deleteById(AppPermissionId id) {
        if (id == null)
            return;

        appPermissionRepository.findById(id).ifPresent(user -> appPermissionRepository.deleteById(id));
    }
}
