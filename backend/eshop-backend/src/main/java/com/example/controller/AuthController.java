package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.dto.RegisterRequest;
import com.example.service_interface.UserService;

@Controller
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new RegisterRequest());

        return "auth/register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("user") RegisterRequest user) {

        System.out.println("Received registration data: " + user);

        userService.registerUser(user);

        return "redirect:/login?registered=true";
    }
}