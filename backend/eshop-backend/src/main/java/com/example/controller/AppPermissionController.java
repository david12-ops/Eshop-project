package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.AppPermission;
import com.example.model.enums.OperationType;
import com.example.model.enums.ResourceType;
import com.example.model.keys.AppPermissionId;
import com.example.service_interface.AppPermissionService;

@RestController 
@RequestMapping("/app-permissions")
public class AppPermissionController {
    private final AppPermissionService appPermissionService;

    public AppPermissionController(AppPermissionService appPermissionService) {
        this.appPermissionService = appPermissionService;
    }

    // LIST
    @GetMapping
    public List<AppPermission> listAppPermissions() {
        return appPermissionService.getAllAppPermissions();
    }

    // DETAIL
    @GetMapping("/{id}")
    public AppPermission appPermissionDetail(@PathVariable AppPermissionId id) {
        return appPermissionService.getAppPermissionById(id);
    }

    // CREATE 
    @PostMapping("/create")
    public AppPermission createAppPermission(@RequestBody AppPermission appPermission) {
        return appPermissionService.saveAppPermission(appPermission);
    }

    // DETAIL
    @GetMapping("/{roleId}/{resourceType}/{operationType}")
    public AppPermission appPermissionDetail(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);
        return appPermissionService.getAppPermissionById(id);
    }

    @PutMapping("/{roleId}/{resourceType}/{operationType}")
    public AppPermission editAppPermission(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType,
            @RequestBody AppPermission appPermission) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        return appPermissionService.editAppPermission(id, appPermission);
    }

    @DeleteMapping("/{roleId}/{resourceType}/{operationType}")
    public void deleteAppPermission(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        appPermissionService.deleteAppPermissionById(id);
    }
}
