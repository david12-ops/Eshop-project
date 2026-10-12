package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Role;
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
    public List<Role> listRoles() {
        return roleService.getAllRoles();
    }

    // DETAIL
    @GetMapping("/{id}")
    public Role roleDetail(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    // CREATE 
    @PostMapping("/create")
    public Role createRole(@RequestBody Role role) {
        return roleService.saveRole(role);
    }

    // EDIT 
    @PutMapping ("/{id}")
    public Role editRole(
            @PathVariable Integer id,
            @RequestBody Role role) {
        return roleService.editRole(id, role);
    }

    // DELETE
    @DeleteMapping ("/{id}")
    public void deleteRole(@PathVariable Integer id) {
        roleService.deleteRoleById(id);
    }
}
