package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.AppPermission;
import com.example.model.enums.OperationType;
import com.example.model.enums.ResourceType;
import com.example.model.keys.AppPermissionId;
import com.example.service_interface.AppPermissionService;

@Controller
@RequestMapping("/app-permissions")
public class AppPermissionController {
    private final AppPermissionService appPermissionService;

    public AppPermissionController(AppPermissionService appPermissionService) {

        this.appPermissionService = appPermissionService;
    }

    // LIST
    @GetMapping
    public String listAppPermissions(Model model) {

        System.out.println("list: " + appPermissionService.findAll());

        model.addAttribute("appPermissions", appPermissionService.findAll());

        return "app-permissions/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String appPermissionDetail(
            @PathVariable AppPermissionId id,
            Model model) {

        model.addAttribute("appPermission", appPermissionService.getAppPermissionById(id));

        return "app-permissions/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("appPermission", new AppPermission());

        model.addAttribute("roles", appPermissionService.findAllRoles());
        model.addAttribute("resourceTypes", ResourceType.values());
        model.addAttribute("operationTypes", OperationType.values());

        return "app-permissions/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createAppPermission(
            @ModelAttribute AppPermission appPermission) {

        appPermissionService.saveAppPermission(appPermission);

        return "redirect:/app-permissions";
    }

    // DETAIL
    @GetMapping("/detail/{roleId}/{resourceType}/{operationType}")
    public String appPermissionDetail(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType,
            Model model) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        model.addAttribute(
                "appPermission",
                appPermissionService.getAppPermissionById(id));

        return "app-permissions/detail";
    }

    @GetMapping("/edit/{roleId}/{resourceType}/{operationType}")
    public String editAppPermission(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType,
            Model model) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        model.addAttribute(
                "appPermission",
                appPermissionService.getAppPermissionById(id));

        model.addAttribute("roles", appPermissionService.findAllRoles());
        model.addAttribute("resourceTypes", ResourceType.values());
        model.addAttribute("operationTypes", OperationType.values());

        return "app-permissions/edit";
    }

    @PostMapping("/edit/{roleId}/{resourceType}/{operationType}")
    public String editAppPermission(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType,
            @ModelAttribute AppPermission appPermission) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        appPermissionService.editAppPermission(id, appPermission);

        return "redirect:/app-permissions";
    }

    @PostMapping("/delete/{roleId}/{resourceType}/{operationType}")
    public String deleteAppPermission(
            @PathVariable Integer roleId,
            @PathVariable ResourceType resourceType,
            @PathVariable OperationType operationType) {

        AppPermissionId id = new AppPermissionId(
                roleId,
                resourceType,
                operationType);

        appPermissionService.deleteAppPermissionById(id);

        return "redirect:/app-permissions";
    }
}
