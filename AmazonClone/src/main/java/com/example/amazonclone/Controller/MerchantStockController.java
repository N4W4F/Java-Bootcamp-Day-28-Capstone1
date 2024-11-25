package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.MerchantStock;
import com.example.amazonclone.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchantstocks")
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/get")
    public ResponseEntity getMerchantStocks() {
        if (merchantStockService.getMerchantStocks() == null)
            return ResponseEntity.status(400).body(new ApiResponse("There are no merchant stocks yet"));

        return ResponseEntity.status(200).body(merchantStockService.getMerchantStocks());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (merchantStockService.addMerchantStock(merchantStock).equals("ok"))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock has been added successfully"));

        else if (merchantStockService.addMerchantStock(merchantStock).equals("null m"))
            return ResponseEntity.status(400).body(new ApiResponse("There are no merchants yet"));

        else if (merchantStockService.addMerchantStock(merchantStock).equals("null p"))
            return ResponseEntity.status(400).body(new ApiResponse("There are no products yet"));

        else if (merchantStockService.addMerchantStock(merchantStock).equals("invalid product"))
            return ResponseEntity.status(400).body(new ApiResponse("Product with ID: " + merchantStock.getProductId() + " was not found"));

        else if (merchantStockService.addMerchantStock(merchantStock).equals("invalid merchant"))
            return ResponseEntity.status(400).body(new ApiResponse("Merchant with ID: " + merchantStock.getMerchantId() + " was not found"));

        return ResponseEntity.status(400).body(new ApiResponse("MerchantStock ID is already used"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (merchantStockService.updateMerchantStock(id, merchantStock).equals("ok"))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock with ID: " + id + " has been updated successfully"));

        else if (merchantStockService.updateMerchantStock(id, merchantStock).equals("invalid product"))
            return ResponseEntity.status(400).body(new ApiResponse("Product with ID: " + merchantStock.getProductId() + " was not found"));

        else if (merchantStockService.updateMerchantStock(id, merchantStock).equals("invalid merchant"))
            return ResponseEntity.status(400).body(new ApiResponse("Merchant with ID: " + merchantStock.getMerchantId() + " was not found"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock with ID: " + id + " was not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchantStock(@PathVariable String id) {
        if (merchantStockService.deleteMerchantStock(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant Stock with ID: " + id + " has been deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock with ID: " + id + " was not found"));
    }

    // 9- Extra Endpoint 'restockProduct' is used to restock a current MerchantStock by any amount
    @PutMapping("/restock/{merchant_id}/{product_id}")
    public ResponseEntity restockProduct(@PathVariable String merchant_id, @PathVariable String product_id, @RequestBody int amount) {
        if (merchantStockService.restockProduct(merchant_id, product_id, amount).equals("ok"))
            return ResponseEntity.status(200).body(new ApiResponse("Product has been restocked successfully"));

        else if (merchantStockService.restockProduct(merchant_id, product_id, amount).equals("invalid amount"))
            return ResponseEntity.status(400).body(new ApiResponse("Amount must be a positive integer"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant Stock does not exist"));
    }
}
