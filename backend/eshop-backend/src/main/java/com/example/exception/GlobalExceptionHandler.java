package com.example.exception;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleNotFound(
            EntityNotFoundException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "error/404";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public String handleResourceNotFound(
            ResourceNotFoundException ex,
            Model model) {

        model.addAttribute("errorMessage", ex.getMessage());

        return "error/404";
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public String handleUserNotFound() {

        return "error/404";
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime() {

        return "error/500";
    }
}