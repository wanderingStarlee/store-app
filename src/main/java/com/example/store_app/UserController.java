package com.example.store_app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.findByUsername(username);
    }

    @PostMapping("/")
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }
}