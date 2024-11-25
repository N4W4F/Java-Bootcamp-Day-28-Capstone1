package com.example.amazonclone.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;


@Data
@AllArgsConstructor
public class User {
    @NotEmpty(message = "User ID cannot be empty")
    private String id;

    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 6, message = "Username must be more than 5 characters")
    private String username;

    @NotEmpty(message = "User Password cannot be empty")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$",
            message = "Password must be at least 6 characters long, and contain both letters and digits")
    private String password;

    @NotEmpty(message = "User Email cannot be empty")
    @Email(message = "User Email must be a valid email")
    private String email;

    @NotEmpty(message = "User Role cannot be empty")
    @Pattern(regexp = "^(Admin|Customer)", message = "User Role must be either Admin or Customer")
    private String role;

    @NotNull(message = "User Balance cannot be empty")
    @Positive(message = "User Balance must be positive number")
    private double balance;

    private ArrayList<String> wishlist = new ArrayList<>();
    private ArrayList<Product> cart = new ArrayList<>();
}
