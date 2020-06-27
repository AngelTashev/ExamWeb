package com.softuni.exam.init;

import com.softuni.exam.model.service.CategoryServiceModel;
import com.softuni.exam.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CategoryInit implements CommandLineRunner {

    private final CategoryService categoryService;

    @Autowired
    public CategoryInit(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (categoryService.isRepositoryEmpty()) {
            this.categoryService.addCategory(new CategoryServiceModel("FOOD", "Perfect for when you get a little bit hungry!"));
            this.categoryService.addCategory(new CategoryServiceModel("DRINK", "When you are feeling thirsty you can get some water or a refreshing cola!"));
            this.categoryService.addCategory(new CategoryServiceModel("HOUSEHOLD", "All of your household needs need organizing!"));
            this.categoryService.addCategory(new CategoryServiceModel("OTHER", "Other products that you might be interested in..."));
        }
    }
}
