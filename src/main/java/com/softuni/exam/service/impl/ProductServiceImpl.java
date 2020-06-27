package com.softuni.exam.service.impl;

import com.softuni.exam.model.entity.Category;
import com.softuni.exam.model.entity.CategoryName;
import com.softuni.exam.model.entity.Product;
import com.softuni.exam.model.service.CategoryServiceModel;
import com.softuni.exam.model.service.ProductServiceModel;
import com.softuni.exam.repository.ProductRepository;
import com.softuni.exam.service.CategoryService;
import com.softuni.exam.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;

    private final CategoryService categoryService;

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ModelMapper modelMapper, CategoryService categoryService, ProductRepository productRepository) {
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(ProductServiceModel productServiceModel) {
        this.productRepository.saveAndFlush(this.modelMapper.map(productServiceModel, Product.class));
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String categoryName) {

        Category category = this.modelMapper.map(this.categoryService.findByName(CategoryName.valueOf(categoryName)), Category.class);
        return this.productRepository.findAllByCategory(category).stream()
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findByName(String name) {
        Product product = this.productRepository.findByName(name).orElse(null);
        if (product == null) {
            return null;
        }
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public double getTotalPrice() {
        double totalPrice = 0;
        for (Product product : this.productRepository.findAll()) {
            System.out.println();
            totalPrice += product.getPrice().doubleValue();
        }
        return totalPrice;
    }

    @Override
    public void deleteById(String id) {
        if (this.productRepository.findById(id).orElse(null) != null) {
            this.productRepository.deleteById(id);
        }
    }

    @Override
    public void deleteAllItems() {
        this.productRepository.deleteAll();
    }
}
