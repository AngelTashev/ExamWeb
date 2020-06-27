package com.softuni.exam.model.service;

public class CategoryServiceModel extends BaseServiceModel {

    private String name;
    private String description;

    public CategoryServiceModel() {
    }

    public CategoryServiceModel(String name) {
        this.name = name;
    }

    public CategoryServiceModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
