package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login() {

        return "auth/login";
    }

    @GetMapping("/home")
    public String home() {

        return "home";
    }

    @GetMapping("/403")
    public String forbidden() {

        return "errors/403";
    }
}