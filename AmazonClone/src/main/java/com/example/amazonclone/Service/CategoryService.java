package com.example.amazonclone.Service;

import com.example.amazonclone.Model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CategoryService {

    ArrayList<Category> categories = new ArrayList<>();

    public ArrayList<Category> getCategories() {
        if (categories.isEmpty())
            return null;

        return categories;
    }

    public String addCategory(Category category) {
        for (Category c : categories) {
            if (c.getId().equals(category.getId())) {
                return "already used";
            }
        }
        categories.add(category);
        return "ok";
    }

    public String updateCategory(String id, Category category) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.set(i, category);
                return "ok";
            }
        }
        return "invalid id";
    }

    public boolean deleteCategory(String id) {
        for (int i = 0; i < categories.size(); i++) {
            if (categories.get(i).getId().equals(id)) {
                categories.remove(i);
                return true;
            }
        }
        return false;
    }
}
