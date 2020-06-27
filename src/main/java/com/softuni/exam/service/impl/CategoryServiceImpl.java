package com.softuni.exam.service.impl;

import com.softuni.exam.model.entity.Category;
import com.softuni.exam.model.entity.CategoryName;
import com.softuni.exam.model.service.CategoryServiceModel;
import com.softuni.exam.repository.CategoryRepository;
import com.softuni.exam.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ModelMapper modelMapper;

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(ModelMapper modelMapper, CategoryRepository categoryRepository) {
        this.modelMapper = modelMapper;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public boolean isRepositoryEmpty() {
        return this.categoryRepository.count() == 0;
    }

    @Override
    public void addCategory(CategoryServiceModel categoryServiceModel) {
        this.categoryRepository.saveAndFlush(this.modelMapper.map(categoryServiceModel, Category.class));
    }

    @Override
    public CategoryServiceModel findByName(CategoryName name) {

        return this.modelMapper.map(this.categoryRepository.findByName(name).orElse(null), CategoryServiceModel.class);
    }
}
