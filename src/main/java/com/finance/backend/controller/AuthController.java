package com.finance.backend.controller;

import com.finance.backend.dto.AuthRequest;
import com.finance.backend.dto.AuthResponse;
import com.finance.backend.model.User;
import com.finance.backend.repository.UserRepository;
import com.finance.backend.security.JwtUtil;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository repo;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository repo, JwtUtil jwtUtil, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        //  Find user
        User user = repo.findAll().stream()
                .filter(u -> u.getEmail().equals(request.email))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found"));

        //  Secure password check
        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        //  Generate JWT
        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token);
    }
}