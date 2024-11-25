package com.example.amazonclone.Controller;

import com.example.amazonclone.ApiResponse.ApiResponse;
import com.example.amazonclone.Model.Category;
import com.example.amazonclone.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity getCategories() {
        if (categoryService.getCategories() == null)
            return ResponseEntity.status(400).body(new ApiResponse("There are no categories yet"));

        return ResponseEntity.status(200).body(categoryService.getCategories());
    }

    @PostMapping("/add")
    public ResponseEntity addCategory(@RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (categoryService.addCategory(category).equals("ok"))
            return ResponseEntity.status(200).body(new ApiResponse("Category has been added successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category ID is already used"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateCategory(@PathVariable String id, @RequestBody @Valid Category category, Errors errors) {
        if (errors.hasErrors())
            return ResponseEntity.status(400).body(errors.getFieldError().getDefaultMessage());

        if (categoryService.updateCategory(id, category).equals("ok"))
            return ResponseEntity.status(200).body(new ApiResponse("Category with ID: " + id + " has been updated successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category with ID: " + id + " was not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteCategory(@PathVariable String id) {
        if (categoryService.deleteCategory(id))
            return ResponseEntity.status(200).body(new ApiResponse("Category with ID: " + id + " has been deleted successfully"));

        return ResponseEntity.status(400).body(new ApiResponse("Category with ID: " + id + " was not found"));
    }
}
