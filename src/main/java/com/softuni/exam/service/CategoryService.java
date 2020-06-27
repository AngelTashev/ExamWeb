package com.softuni.exam.service;

import com.softuni.exam.model.entity.CategoryName;
import com.softuni.exam.model.service.CategoryServiceModel;

public interface CategoryService {
    boolean isRepositoryEmpty();
    void addCategory(CategoryServiceModel categoryServiceModel);
    CategoryServiceModel findByName(CategoryName name);
}
