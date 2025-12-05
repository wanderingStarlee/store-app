package com.example.store_app;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/addToOrder")
public class AddToOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping
    public String addToOrder(@RequestParam Long productId, HttpSession session) {
        // Достаем пользователя из сессии
        User currentUser = (User) session.getAttribute("user");

        if (currentUser == null) {
            return "redirect:/login";
        }

        Product product = productService.getProductById(productId);
        if (product != null) {
            Order order = new Order();
            order.setUser(currentUser);
            order.setTotalAmount(product.getPrice());
            orderService.createOrder(order);
        }

        return "redirect:/computer_components";
    }
}