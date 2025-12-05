package com.example.store_app;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderRepository orderRepository; // Чтобы достать заказы

    // Страница профиля
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // Обновляем данные пользователя из БД, чтобы видеть актуальные
        user = userService.findById(user.getId()).orElse(user);

        // Получаем список заказов этого пользователя
        List<Order> userOrders = orderRepository.findByUserIdOrderByOrderDateDesc(user.getId());

        model.addAttribute("user", user);
        model.addAttribute("orders", userOrders);

        return "profile";
    }

    // Обновление данных
    @PostMapping("/profile/update")
    public String updateProfile(@RequestParam String email,
                                @RequestParam String password,
                                HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) return "redirect:/login";

        // Находим пользователя в БД и обновляем поля
        User userFromDb = userService.findById(user.getId()).orElse(null);
        if (userFromDb != null) {
            userFromDb.setEmail(email);
            if (!password.isEmpty()) { // Меняем пароль только если введен новый
                userFromDb.setPasswordHash(password);
            }
            userService.save(userFromDb);

            // Обновляем сессию
            session.setAttribute("user", userFromDb);
        }

        return "redirect:/profile?success=true";
    }
}