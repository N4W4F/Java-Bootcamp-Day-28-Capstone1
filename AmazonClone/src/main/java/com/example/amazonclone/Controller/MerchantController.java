package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Merchant;
import com.example.amazonclone.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/get")
    public ResponseEntity getMerchants() {
        if (merchantService.getMerchants() == null)
            return ResponseEntity.status(400).body(new ApiResponse("There are no merchants yet"));

        return ResponseEntity.status(200).body(merchantService.getMerchants());
    }

    @PostMapping("/add")
    public ResponseEntity addMerchant(@RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (merchantService.addMerchant(merchant).equals("ok"))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant has been added successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant ID is already used"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateMerchant(@PathVariable String id, @RequestBody @Valid Merchant merchant, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (merchantService.updateMerchant(id, merchant).equals("ok"))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant with ID: " + id + " has been updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant with ID: " + id + " was not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteMerchant(@PathVariable String id) {
        if (merchantService.deleteMerchant(id))
            return ResponseEntity.status(200).body(new ApiResponse("Merchant with ID: " + id + " has been deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Merchant with ID: " + id + " was not found"));
    }
}
