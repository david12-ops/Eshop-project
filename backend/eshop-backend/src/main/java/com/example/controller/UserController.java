package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.example.model.AppUser;
import com.example.service_interface.RoleService;
import com.example.service_interface.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    public UserController(
            UserService userService,
            RoleService roleService) {

        this.userService = userService;
        this.roleService = roleService;
    }

    // LIST
    @GetMapping
    public String listUsers(Model model) {

        model.addAttribute("users", userService.getAllUsers());

        return "users/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String userDetail(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute("user", userService.getUserById(id));

        return "users/detail";
    }

    // CREATE FORM
    @GetMapping("/create")
    public String createForm(Model model) {

        model.addAttribute("user", new AppUser());

        model.addAttribute("roles", roleService.findAll());

        return "users/create";
    }

    // CREATE SAVE
    @PostMapping("/create")
    public String createUser(
            @ModelAttribute AppUser user) {

        userService.saveUser(user);

        return "redirect:/users";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        model.addAttribute("user", userService.getUserById(id));

        model.addAttribute("roles", roleService.findAll());

        return "users/edit";
    }

    // EDIT SAVE
    @PostMapping("/edit/{id}")
    public String editUser(
            @PathVariable Integer id,
            @ModelAttribute AppUser user) {

        userService.editUser(id, user);

        return "redirect:/users";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteUser(
            @PathVariable Integer id) {

        userService.deleteUserById(id);

        return "redirect:/users";
    }
}
