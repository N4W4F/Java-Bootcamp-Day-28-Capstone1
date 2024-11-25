package com.example.amazonclone.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MerchantStock {
    @NotEmpty(message = "Merchant Stock ID cannot be empty")
    private String id;

    @NotEmpty(message = "Product ID for Merchant Stock cannot be empty")
    private String productId;

    @NotEmpty(message = "Merchant ID for Merchant Stock cannot be empty")
    private String merchantId;

    @NotNull(message = "Merchant Stock cannot be empty")
    @Min(value = 10, message = "Merchant Stock must be 10 at start")
    private int stock;
}
