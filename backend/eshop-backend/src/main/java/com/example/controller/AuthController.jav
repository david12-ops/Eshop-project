package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {

        return "auth/login";
    }    
    
    @GetMapping("/register")
    public String register(Model model) {

        model.addAttribute("user", new AppUser());

        return "auth/register";
    }

    @PostMapping
    public String register(
            @ModelAttribute AppUser user) {

        userService.registerUser(user);

        return "redirect:/login?registered=true";
    }

    @GetMapping("/adminHome")
    public String adminHome() {

        return "adminHome";
    }

    @GetMapping("/stuffHome")
    public String stuffHome() {

        return "stuffHome";
    }

    @GetMapping("/customerHome")
    public String customerHome() {

        return "customerHome";
    }

    @GetMapping("/403")
    public String forbidden() {

        return "errors/403";
    }
}