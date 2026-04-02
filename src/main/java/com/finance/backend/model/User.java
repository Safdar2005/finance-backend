package com.finance.backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    // Primary Key
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // User name
    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    // Email (unique + validated)
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    @Column(unique = true, nullable = false)
    private String email;

    // Password
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    // Active / Inactive
    @Column(nullable = false)
    private boolean active = true;

    // Role
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    // Default constructor
    public User() {}

    // Parameterized constructor
    public User(String name, String email, String password, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.active = true;
    }

    // ================= GETTERS =================
    public Long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getPassword() { return password; }

    public boolean isActive() { return active; }

    public Role getRole() { return role; }

    // ================= SETTERS =================
    public void setName(String name) { this.name = name; }

    public void setEmail(String email) { this.email = email; }

    public void setPassword(String password) { this.password = password; }

    public void setActive(boolean active) { this.active = active; }

    public void setRole(Role role) { this.role = role; }
}