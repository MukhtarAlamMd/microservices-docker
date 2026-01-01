package com.ecomics.user_service.controller;

import com.ecomics.user_service.dto.AuthResponse;
import com.ecomics.user_service.dto.LoginRequest;
import com.ecomics.user_service.dto.RegisterAdminRequest;
import com.ecomics.user_service.dto.RegisterUserRequest;
import com.ecomics.user_service.entity.User;
import com.ecomics.user_service.repository.UserRepository;
import com.ecomics.user_service.security.JwtUtil;
import com.ecomics.user_service.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthService authService;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    // PUBLIC USER REGISTRATION
    @PostMapping("/register-user")
    public String registerUser(@Valid @RequestBody RegisterUserRequest request) {
        authService.registerUser(request);
        return "USER registered successfully";
    }

    // ADMIN REGISTRATION (normally protected later)
    @PostMapping("/register-admin")
    public String registerAdmin(@Valid @RequestBody RegisterAdminRequest request) {
        authService.registerAdmin(request);
        return "ADMIN registered successfully";
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestParam String refreshToken) {

        String username = jwtUtil.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String newAccessToken = jwtUtil.generateAccessToken(
                user.getUsername(),
                user.getRole().name()
        );

        String newRefreshToken = jwtUtil.generateRefreshToken(
                user.getUsername()
        );

        return new AuthResponse(newAccessToken, newRefreshToken);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return (AuthResponse) authService.login(request);
    }
}
