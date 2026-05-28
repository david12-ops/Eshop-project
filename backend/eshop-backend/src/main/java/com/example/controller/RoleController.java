package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.Role;
import com.example.model.enums.RoleType;
import com.example.service_interface.RoleService;

@Controller
@RequestMapping("/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {

        this.roleService = roleService;
    }

    // LIST
    @GetMapping
    public String listRoles(Model model) {

        model.addAttribute("roles", roleService.findAll());

        return "roles/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String roleDetail(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute("role", roleService.getRoleById(id));

        return "roles/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("role", new Role());

        model.addAttribute("roleTypes", RoleType.values());

        return "roles/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createRole(
            @ModelAttribute Role role) {

        roleService.saveRole(role);

        return "redirect:/roles";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute("role", roleService.getRoleById(id));

        model.addAttribute("roleTypes", RoleType.values());

        return "roles/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editRole(
            @PathVariable Integer id,
            @ModelAttribute Role role) {

        roleService.editRole(id, role);

        return "redirect:/roles";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteRole(
            @PathVariable Integer id) {

        roleService.deleteRoleById(id);

        return "redirect:/roles";
    }
}
