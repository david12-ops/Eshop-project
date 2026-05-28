package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Product;
import com.example.service_interface.ProductService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/cart")
public class CartController {

    private final ProductService productService;

    public CartController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/add/{id}")
    public String addToCart(
            @PathVariable Integer id,
            HttpSession session) {

        Product product = productService.getProductById(id);

        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        cart.add(product);

        session.setAttribute("cart", cart);

        return "redirect:/";
    }

    @GetMapping
    public String cartPage(
            HttpSession session,
            Model model) {

        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart == null) {
            cart = new ArrayList<>();
        }

        model.addAttribute("cart", cart);

        return "cart/index";
    }

    @PostMapping("/remove/{index}")
    public String removeItem(
            @PathVariable Integer index,
            HttpSession session) {

        List<Product> cart = (List<Product>) session.getAttribute("cart");

        if (cart != null && index < cart.size()) {
            cart.remove((int) index);
        }

        session.setAttribute("cart", cart);

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session) {

        session.removeAttribute("cart");

        return "redirect:/cart";
    }
}