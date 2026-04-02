package com.finance.backend.controller;

import com.finance.backend.model.User;
import com.finance.backend.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public User create(@RequestBody User user) {
        return service.createUser(user);
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAllUsers();
    }

    @PutMapping("/{id}/status")
    public User updateStatus(@PathVariable Long id, @RequestParam boolean active) {
        return service.updateStatus(id, active);
    }
}