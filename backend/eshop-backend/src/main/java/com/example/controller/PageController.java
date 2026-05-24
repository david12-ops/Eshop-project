package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String root() {

        return "redirect:/adminHome";
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

    @GetMapping("/login")
    public String login() {

        return "auth/login";
    }

    @GetMapping("/register")
    public String register() {

        return "auth/register";
    }

    @GetMapping("/403")
    public String forbidden() {

        return "errors/403";
    }
}
