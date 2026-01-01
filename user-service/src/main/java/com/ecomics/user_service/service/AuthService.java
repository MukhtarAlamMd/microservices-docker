package com.ecomics.user_service.service;

import com.ecomics.user_service.dto.AuthResponse;
import com.ecomics.user_service.dto.LoginRequest;
import com.ecomics.user_service.dto.RegisterAdminRequest;
import com.ecomics.user_service.dto.RegisterUserRequest;
import com.ecomics.user_service.entity.Role;
import com.ecomics.user_service.entity.User;
import com.ecomics.user_service.repository.UserRepository;
import com.ecomics.user_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
   private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String registerAdmin(RegisterAdminRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.ADMIN);

        userRepository.save(user);

        return "User registered successfully";
    }


    // ✅ REGISTER USER
    public String registerUser(RegisterUserRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        return "User registered successfully";
    }

    public AuthResponse login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid credentials");
        }

        String accessToken = jwtUtil.generateAccessToken(
                user.getUsername(),
                user.getRole().name()
        );

        String refreshToken = jwtUtil.generateRefreshToken(
                user.getUsername()
        );

        return new AuthResponse(accessToken, refreshToken);
    }

}
