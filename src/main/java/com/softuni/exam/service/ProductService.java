package com.softuni.exam.service;

import com.softuni.exam.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {
    void addProduct(ProductServiceModel productServiceModel);
    List<ProductServiceModel> findAllByCategory(String categoryName);
    ProductServiceModel findByName(String name);
    double getTotalPrice();
    void deleteById(String id);
    void deleteAllItems();
}
