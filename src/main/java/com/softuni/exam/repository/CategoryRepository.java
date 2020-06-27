package com.softuni.exam.repository;

import com.softuni.exam.model.entity.Category;
import com.softuni.exam.model.entity.CategoryName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
    Optional<Category> findByName(CategoryName name);
}
