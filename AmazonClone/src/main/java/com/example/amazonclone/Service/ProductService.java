package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Category;
import com.example.amazonclone.Model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class ProductService {

    ArrayList<Product> products = new ArrayList<>();

    private final CategoryService categoryService;

    public ArrayList<Product> getProducts() {
        if (products.isEmpty())
            return null;

        return products;
    }

    public String addProduct(Product product) {
        for (Product p : products) {
            if (p.getId().equals(product.getId())) {
                return "already used";
            }
        }
        if (categoryService.getCategories() == null)
            return "null";

        for (Category c : categoryService.getCategories()) {
            if (c.getId().equals(product.getCategoryId())) {
                products.add(product);
                return "ok";
            }
        }
        return "invalid category";
    }

    public String updateProduct(String id, Product product) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                for (Category c : categoryService.getCategories()) {
                    if (c.getId().equals(product.getCategoryId())) {
                        products.add(product);
                        return "ok";
                    }
                } return "invalid category";
            }
        }
        return "invalid id";
    }

    public boolean deleteProduct(String id) {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getId().equals(id)) {
                products.remove(i);
                return true;
            }
        }
        return false;
    }
}
