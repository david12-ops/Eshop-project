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

    @GetMapping("/login")
    public String login() {

        return "auth/login";
    }

    @GetMapping("/403")
    public String forbidden() {

        return "errors/403";
    }
}
