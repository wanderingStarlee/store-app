package com.example.store_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/computer_components")
public class ComputerComponentsController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public String showComputerComponents(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "computer_components";
    }
}