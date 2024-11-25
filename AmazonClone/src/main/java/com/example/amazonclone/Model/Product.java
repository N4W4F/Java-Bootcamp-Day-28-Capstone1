package com.example.amazonclone.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "Product ID cannot be empty")
    private String id;

    @NotEmpty(message = "Product Name cannot be empty")
    @Size(min = 4, message = "Product Name must be more than 3 characters")
    private String name;

    @NotNull(message = "Product Price cannot be empty")
    @Positive(message = "Product Price must be a positive number")
    private double price;

    @NotEmpty(message = "Category ID cannot be empty")
    private String categoryId;
}
