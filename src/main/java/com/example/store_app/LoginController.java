package com.example.store_app;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String showLoginForm() {
        return "login";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Удаляет сессию пользователя
        return "redirect:/login";
    }


    @PostMapping
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.findByUsername(username);

        // Простая проверка пароля (для реального проекта нужен BCrypt)
        if (user != null && user.getPasswordHash().equals(password)) {
            // Сохраняем пользователя в сессию
            session.setAttribute("user", user);
            return "redirect:/computer_components";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "login";
        }
    }
}
