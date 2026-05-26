package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.security.core.Authentication;

@Controller
public class PageController {

    @GetMapping("/login")
    public String login() {

        return "auth/login";
    }

    @GetMapping("/")
    public String root(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login";
        }

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/adminHome";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_STAFF"))) {
            return "redirect:/staffHome";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_CUSTOMER"))) {
            return "redirect:/customerHome";
        }

        return "redirect:/login?error=true";
    }

    @GetMapping("/adminHome")
    public String adminHome() {

        return "adminHome";
    }

    @GetMapping("/staffHome")
    public String staffHome() {

        return "staffHome";
    }

    @GetMapping("/customerHome")
    public String customerHome() {

        return "customerHome";
    }

    @GetMapping("/403")
    public String forbidden() {

        return "error/403";
    }
}
