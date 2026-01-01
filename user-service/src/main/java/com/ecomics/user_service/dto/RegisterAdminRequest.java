package com.ecomics.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterAdminRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 4, max = 20, message = "Username must be 4–20 characters")
    @Pattern(
            regexp = "^[a-zA-Z0-9._]+$",
            message = "Username can contain letters, numbers, . and _ only"
    )
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!]).*$",
            message = "Password must contain 1 uppercase, 1 number, 1 special character"
    )
    private String password;
    private String role;
}