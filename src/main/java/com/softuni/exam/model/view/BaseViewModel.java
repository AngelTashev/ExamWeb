package com.softuni.exam.model.view;

public abstract class BaseViewModel {

    private String id;

    public BaseViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
