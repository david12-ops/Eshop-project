package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.RegisterRequestDTO;
import com.example.service_interface.AppUserService;

@Controller
public class AuthController {
    private final AppUserService appUserService;

    public AuthController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new RegisterRequestDTO());

        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("user") RegisterRequestDTO user) {

        System.out.println("Received registration data: " + user);

        appUserService.registerUser(user);

        return "redirect:/login?registered=true";
    }
}