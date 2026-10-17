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

import com.example.dto.RoleDTO;
import com.example.model.MyUserDetail;
import com.example.service_interface.RoleService;

@RestController
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    // LIST
    @GetMapping
    public List<RoleDTO> listRoles(@AuthenticationPrincipal MyUserDetail userDetail) {
        return roleService.getAllRoles(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public RoleDTO roleDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return roleService.getRoleById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public RoleDTO createRole(
            @RequestBody RoleDTO roleDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return roleService.saveRole(roleDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public RoleDTO editRole(
            @PathVariable Integer id,
            @RequestBody RoleDTO roleDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return roleService.editRole(id, roleDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteRole(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        roleService.deleteRoleById(id, userDetail);
    }
}
