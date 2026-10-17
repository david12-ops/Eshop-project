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

import com.example.dto.AppUserDTO;
import com.example.model.MyUserDetail;
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
    public List<AppUserDTO> listUsers(@AuthenticationPrincipal MyUserDetail userDetail) {
        return appUserService.getAppUsers(userDetail);
    }

    // DETAIL
    @GetMapping("/{id}")
    public AppUserDTO userDetail(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return appUserService.getAppUserById(id, userDetail);
    }

    // CREATE
    @PostMapping("/create")
    public AppUserDTO createUser(
            @RequestBody AppUserDTO appUserDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return appUserService.saveAppUser(appUserDTO, userDetail);
    }

    // EDIT
    @PutMapping("/{id}")
    public AppUserDTO editUser(
            @PathVariable Integer id,
            @RequestBody AppUserDTO appUserDTO,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        return appUserService.editAppUser(id, appUserDTO, userDetail);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public void deleteUser(
            @PathVariable Integer id,
            @AuthenticationPrincipal MyUserDetail userDetail) {
        appUserService.deleteAppUserById(id, userDetail);
    }
}
