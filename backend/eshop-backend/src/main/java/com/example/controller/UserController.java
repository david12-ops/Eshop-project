package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.AppUser;
import com.example.service_interface.AppUserService;

@RestController 
@RequestMapping("/users")
public class UserController {
    private final AppUserService appUserService;

    public UserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    // LIST
    @GetMapping
    public List<AppUser> listUsers() {
        return appUserService.getAppUsers();
    }

    // DETAIL
    @GetMapping("/{id}")
    public AppUser userDetail(@PathVariable Integer id) {
        return appUserService.getAppUserById(id);
    }

    // CREATE 
    @PostMapping("/create")
    public AppUser createUser(@RequestBody  AppUser user) {
        return appUserService.saveAppUser(user);
    }

    // EDIT 
    @PutMapping("/{id}")
    public AppUser editUser(
            @PathVariable Integer id,
            @RequestBody  AppUser user) {
        return appUserService.editAppUser(id, user);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        appUserService.deleteAppUserById(id);
    }
}
