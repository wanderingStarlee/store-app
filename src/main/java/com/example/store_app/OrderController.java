package com.example.store_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserService userService;

    @GetMapping
    public String showOrderForm(Model model) {
        model.addAttribute("order", new Order());
        return "order";
    }

    @PostMapping
    public String createOrder(@RequestParam Long userId, @RequestParam Double totalAmount, Model model) {
        Optional<User> optionalUser = userService.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Order order = new Order();
            order.setUser(user);
            order.setTotalAmount(totalAmount);
            orderService.createOrder(order);
            return "redirect:/";
        } else {
            // Обработка ошибки, если пользователь не найден
            return "error";
        }
    }
}