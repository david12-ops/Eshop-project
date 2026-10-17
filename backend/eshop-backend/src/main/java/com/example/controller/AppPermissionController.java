package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.AppPermissionDTO;
import com.example.model.MyUserDetail;
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
    public List<AppPermissionDTO> listAppPermissions(@AuthenticationPrincipal MyUserDetail userDetail) {
        return appPermissionService.getAllAppPermissions(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public AppPermissionDTO appPermissionDetail(
            @PathVariable AppPermissionId id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return appPermissionService.getAppPermissionById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public AppPermissionDTO createAppPermission(
            @RequestBody AppPermissionDTO appPermissionDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return appPermissionService.saveAppPermission(appPermissionDTO, userDetail);
    }

    // DETAIL
    @GetMapping("/{roleId}/{resourceType}/{operationType}")
    public AppPermissionDTO appPermissionDetail(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType,
            @AuthenticationPrincipal MyUserDetail userDetail) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);
        return appPermissionService.getAppPermissionById(id, userDetail);
    }

    @PutMapping("/{roleId}/{resourceType}/{operationType}")
    public AppPermissionDTO editAppPermission(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType,
            @RequestBody AppPermissionDTO appPermissionDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        return appPermissionService.editAppPermission(id, appPermissionDTO, userDetail);
    }

    @DeleteMapping("/{roleId}/{resourceType}/{operationType}")
    public void deleteAppPermission(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType,
            @AuthenticationPrincipal MyUserDetail userDetail) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        appPermissionService.deleteAppPermissionById(id, userDetail);
    }
}
