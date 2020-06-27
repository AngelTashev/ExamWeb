package com.softuni.exam.repository;

import com.softuni.exam.model.entity.Category;
import com.softuni.exam.model.entity.CategoryName;
import com.softuni.exam.model.entity.Product;
import com.softuni.exam.model.service.CategoryServiceModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {

    List<Product> findAllByCategory(Category category);
    Optional<Product> findByName(String name);
}
